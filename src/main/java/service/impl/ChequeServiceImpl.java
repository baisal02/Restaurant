package service.impl;

import dto.*;
import entities.Cheque;
import entities.MenuItem;
import entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repositories.ChequeRepo;
import repositories.MenuItemRepo;
import repositories.UserRepo;
import service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

        User user = userRepo.findById(waiterId).
                orElseThrow(()->new RuntimeException("User not found"));

        List<String>foods = chequeRequest.getFoodNames();
        List<MenuItem>menuItems = new ArrayList<>();

        for (String foodname : foods) {
            MenuItem menuItem = menuItemRepo.findMenuItemByName(foodname);
            if(menuItem != null) {
                menuItems.add(menuItem);
            }
            else {return new SimpleResponse("Menu item not found: "+foodname, HttpStatus.BAD_REQUEST);}
        }

        Long totalPrice = menuItems.stream()
                .mapToInt(MenuItem::getPrice)
                .asLongStream()
                .sum();

        Cheque cheque = new Cheque();
        cheque.setDate(chequeRequest.getCreatedAt());
        cheque.setMenuItems(menuItems);
        cheque.setUser(user);
        cheque.setPriceAverage(totalPrice);
        user.getCheques().add(cheque);
        chequeRepo.save(cheque);
        userRepo.save(user);

        return new SimpleResponse("cheque has been created", HttpStatus.OK);
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
        List<String>foodnames = chequeRequest.getFoodNames();
        List<MenuItem>menuItems = new ArrayList<>();
        int totalPrice = 0;
        for (String foodname : foodnames) {
            MenuItem menuItem = menuItemRepo.findMenuItemByName(foodname);
            if(menuItem != null) {
                menuItems.add(menuItem);
                totalPrice += menuItem.getPrice();
            }
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

       List<MenuItem>menuItems = cheque.getMenuItems();
       menuItems.stream().forEach(menuItem -> {menuItem.getCheques().remove(cheque);});

       chequeRepo.delete(cheque);
        return new SimpleResponse("cheque has been deleted", HttpStatus.OK);
    }

    @Override
    public TotalAmountOneDay getTotalAmountOneDay(LocalDate date) {
        AtomicReference<Long> totalamount = new AtomicReference<>(0l);
        List<Cheque>cheques = chequeRepo.findAll();
        cheques.stream().forEach(cheque -> {if(cheque.getDate().equals(date)) {
            totalamount.updateAndGet(v -> v + cheque.getPriceAverage());
        }});
        TotalAmountOneDay totalAmountOneDay = new TotalAmountOneDay();
        totalAmountOneDay.setTotalAmount(totalamount.get());
        return totalAmountOneDay;
    }
}
