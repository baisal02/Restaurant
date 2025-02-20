package org.example.restaurant.service.impl;

import org.example.restaurant.entities.Cheque;
import org.example.restaurant.entities.MenuItem;
import org.example.restaurant.entities.User;
import org.example.restaurant.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.restaurant.repositories.ChequeRepo;
import org.example.restaurant.repositories.MenuItemRepo;
import org.example.restaurant.repositories.UserRepo;
import org.example.restaurant.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ChequeServiceImpl implements ChequeService {
    private final UserRepo userRepo;
    private final ChequeRepo chequeRepo;
    private final MenuItemRepo menuItemRepo;

    public ChequeServiceImpl(UserRepo userRepo, ChequeRepo chequeRepo, MenuItemRepo menuItemRepo) {
        this.userRepo = userRepo;
        this.chequeRepo = chequeRepo;
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public SimpleResponse createCheque(Long waiterId, ChequeRequest chequeRequest) {

        // Find waiter
        User user = userRepo.findById(waiterId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if foodIds are provided
        if (chequeRequest.getFoodIds() == null || chequeRequest.getFoodIds().isEmpty()) {
            return new SimpleResponse("No food items selected", HttpStatus.BAD_REQUEST);
        }

        // Fetch menu items from the repository
        List<MenuItem> menuItems = chequeRequest.getFoodIds().stream()
                .map(foodId -> menuItemRepo.findById(foodId)
                        .orElseThrow(() -> new RuntimeException("MenuItem not found for ID: " + foodId)))
                .collect(Collectors.toList());

        // Calculate total price
        int totalPrice = menuItems.stream()
                .mapToInt(MenuItem::getPrice)
                .sum();

        // Update restaurant revenue if user belongs to a restaurant
        if (user.getRestaurant() != null) {
            user.getRestaurant().addChequePrice((long) totalPrice);
        }

        // Create and save cheque
        Cheque cheque = new Cheque();
        cheque.setDate(chequeRequest.getCreatedAt());
        cheque.setMenuItems(menuItems);
        cheque.setUser(user);
        cheque.setPriceAverage((long) totalPrice);


        // Ensure the user's cheque list is initialized
        if (user.getCheques() == null) {
            user.setCheques(new ArrayList<>());
        }
        user.getCheques().add(cheque);

        chequeRepo.save(cheque);
        userRepo.save(user);

        return new SimpleResponse("Cheque has been created", HttpStatus.OK);
    }


    @Override
    public ChequeREESPONSE getChequeInfo(Long chequeId) {

        Cheque cheque = chequeRepo.findById(chequeId).
                orElseThrow(()->new RuntimeException("not found"));
        List<MenuItem>menuItems = cheque.getMenuItems();
        List<MenuItemResponse>menuItemResponses = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            menuItemResponses.add(new MenuItemResponse(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getImage(),
                    menuItem.getPrice(),
                    menuItem.getDescription(),
                    menuItem.isVegetarian()
            ));
        }

        ChequeResponse chequeResponse = new ChequeResponse();
        chequeResponse.setChequeId(cheque.getId());
        chequeResponse.setCreatedAt(cheque.getDate());
        chequeResponse.setMenuItemResponses(menuItemResponses);
        chequeResponse.setPriceAverage(cheque.getPriceAverage());

        ChequeREESPONSE finalResponse = new ChequeREESPONSE();
        finalResponse.setChequeResponse(chequeResponse);
        finalResponse.setWaiterName(cheque.getUser().getFirstName());
        finalResponse.setService(BigDecimal.
                valueOf(cheque.getPriceAverage()).
                multiply(new BigDecimal("0.10")));

        BigDecimal priceAverage = new BigDecimal(cheque.getPriceAverage());
        BigDecimal serviceCharge = priceAverage.multiply(new BigDecimal("0.10"));
        BigDecimal grandTotal = priceAverage.add(serviceCharge);
        finalResponse.setGrandTotal(grandTotal.longValue());

        return finalResponse;
    }

    @Override
    public SimpleResponse updateCheque(ChequeRequest chequeRequest, Long chequeId) {
        Cheque cheque = chequeRepo.findById(chequeId).
                orElseThrow(()->new RuntimeException("not found"));
        List<Long>foodnames = chequeRequest.getFoodIds();
        List<MenuItem>menuItems = new ArrayList<>();
        int totalPrice = 0;
        for (Long foodId : foodnames) {
            MenuItem menuItem = menuItemRepo.findById(foodId).orElseThrow(()->new RuntimeException("MenuItem not found"));
                menuItems.add(menuItem);
                totalPrice += menuItem.getPrice();

        }
        cheque.setMenuItems(menuItems);
        cheque.setDate(chequeRequest.getCreatedAt());
        cheque.setPriceAverage((long) totalPrice);
        chequeRepo.save(cheque);
        return new SimpleResponse("cheque has been updated", HttpStatus.OK);
    }

    @Override
    public SimpleResponse deleteCheque(Long chequeId) {
        Cheque cheque = chequeRepo.
                findById(chequeId).orElseThrow(()->new RuntimeException("not found"));

        //breaking relations
       User user = cheque.getUser();
       user.getCheques().remove(cheque);
       cheque.setUser(null);

       //no need in breaking relation, since they have manytomany relation,
        // hibernate will break it (cutting row in third table)
       /*List<MenuItem>menuItems = cheque.getMenuItems();
       menuItems.stream().forEach(menuItem -> {menuItem.getCheques().remove(cheque);});
        */
       chequeRepo.delete(cheque);
        return new SimpleResponse("cheque has been deleted", HttpStatus.OK);
    }

    @Override
    public TotalAmountOneDay getTotalAmountOneDay(LocalDate date) {
        AtomicReference<Long> totalamount = new AtomicReference<>(0l);
        List<Cheque>cheques = chequeRepo.findAll();
        for(Cheque cheque:cheques){
            if(cheque.getDate().equals(date)){
            cheque.getMenuItems().forEach(menuItem -> {totalamount.set(totalamount.get()+menuItem.getPrice());});
            }
        }
        TotalAmountOneDay totalAmountOneDay = new TotalAmountOneDay();
        totalAmountOneDay.setTotalAmount(totalamount.get().intValue());
        return totalAmountOneDay;
    }
}
