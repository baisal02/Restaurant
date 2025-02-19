package org.example.restaurant.service.impl;

import org.example.restaurant.entities.Cheque;
import org.example.restaurant.entities.MenuItem;
import org.example.restaurant.entities.Restaurant;
import org.example.restaurant.entities.Subcategory;
import org.example.restaurant.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.restaurant.repositories.ChequeRepo;
import org.example.restaurant.repositories.MenuItemRepo;
import org.example.restaurant.repositories.RestaurantRepo;
import org.example.restaurant.service.MenuItemService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepo menuItemRepo;
    private final RestaurantRepo restaurantRepo;
    private final ChequeRepo chequeRepo;

    public MenuItemServiceImpl(MenuItemRepo menuItemRepo, RestaurantRepo restaurantRepo, ChequeRepo chequeRepo) {
        this.menuItemRepo = menuItemRepo;
        this.restaurantRepo = restaurantRepo;
        this.chequeRepo = chequeRepo;
    }

    @Override
    public SimpleResponse addMenuItem(MenuItemRequest menuItemRequest, Long restaurantId) {

        Restaurant restaurant = restaurantRepo.
                findById(restaurantId).
                orElseThrow(()->new RuntimeException("Restaurant not found"));


        if (menuItemRequest.getPrice()>=0) {
            MenuItem menuItem = new MenuItem();
            menuItem.setName(menuItemRequest.getName());
            menuItem.setImage(menuItemRequest.getImage());
            menuItem.setPrice(menuItemRequest.getPrice());
            menuItem.setDescription(menuItemRequest.getDescription());
            menuItem.setVegetarian(menuItem.isVegetarian());
            menuItem.setRestaurant(restaurant);
            menuItemRepo.save(menuItem);
            restaurant.getMenuItemList().add(menuItem);
            return new SimpleResponse("The food has been added to the Restaurant", HttpStatus.OK);
        }
        return new SimpleResponse("Failed", HttpStatus.BAD_REQUEST);
    }

    @Override
    public MenuItemResponceCategory getMenuItemById(Long menuItemId) {
        MenuItem menuItem = menuItemRepo.findById(menuItemId).
                orElseThrow(()->new RuntimeException("not found"));

        MenuItemResponse menuItemResponse = new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getImage(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.isVegetarian()
        );


        MenuItemResponceCategory menuItemResponceCategory = new MenuItemResponceCategory();
        if(menuItem.getSubcategory()!=null) {
        menuItemResponceCategory.setCategoryName(menuItem.getSubcategory().getName());}
        menuItemResponceCategory.setMenuItemResponse(menuItemResponse);

        return menuItemResponceCategory;
    }

    @Override
    public SimpleResponse updateMenuItem(MenuItemRequest menuItemRequest, Long menuItemId) {
        MenuItem menuItem = menuItemRepo.
                findById(menuItemId).orElseThrow(()->new RuntimeException("not found"));

        if(menuItemRequest.getPrice()>=0){
            menuItem.setName(menuItemRequest.getName());
            menuItem.setImage(menuItemRequest.getImage());
            menuItem.setPrice(menuItemRequest.getPrice());
            menuItem.setDescription(menuItemRequest.getDescription());
            menuItem.setVegetarian(menuItemRequest.isVegetarian());
            menuItemRepo.save(menuItem);
            return new SimpleResponse("The food has been updated", HttpStatus.OK);
        }

        return new SimpleResponse("Failed", HttpStatus.BAD_REQUEST);
    }

    @Override
    public SimpleResponse deleteMenuItem(Long menuItemId){
        MenuItem menuItem = menuItemRepo.findById(menuItemId).
                orElseThrow(()->new RuntimeException("not found"));

        //breaking relation-----------------------------------------------
        List<Cheque> chequeList = menuItem.getCheques();
        Restaurant restaurant = menuItem.getRestaurant();
        Subcategory subcategory = menuItem.getSubcategory();
        if(chequeList!=null && restaurant!=null&&subcategory!=null) {
        chequeList.forEach(cheque->{cheque.getMenuItems().remove(menuItem);});
        chequeRepo.saveAll(chequeList);

        restaurant.getMenuItemList().remove(menuItem);

        subcategory.getMenuItems().remove(menuItem);
        menuItem.setSubcategory(null);
        menuItemRepo.delete(menuItem);
        return new SimpleResponse("The food has been deleted", HttpStatus.NO_CONTENT);
        }
        //------------------------------------------------------------------------
        return new SimpleResponse("Failed", HttpStatus.BAD_REQUEST);
    }

    @Override
    public MenuItemSpecailResponse getMenuItemWithFirstCharacter(String firstCharacter) {
        List<MenuItem> menuItemList = menuItemRepo.findByNameStartingWith(firstCharacter);
        List<MenuItemResponse>responseList=new ArrayList<>();

        for(MenuItem menuItem:menuItemList) {
                responseList.add(new MenuItemResponse(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getImage(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        menuItem.isVegetarian()
                ));
        }
        //creating response value
        MenuItemSpecailResponse menuItemSpecailResponse=new MenuItemSpecailResponse();
        menuItemSpecailResponse.setMenuItemResponses(responseList);
        return menuItemSpecailResponse;
    }

    @Override
    public MenuItemSpecailResponse getSorttedMenuItems(String sortType) {
        List<MenuItem> menuItemList = menuItemRepo.findAll();

        List<MenuItem> menuItemsAscending = new ArrayList<>(menuItemList);
        List<MenuItem> menuItemsDescending = new ArrayList<>(menuItemList);

        List<MenuItemResponse>responseList  = new ArrayList<>();


        Collections.sort(menuItemsAscending, Comparator.comparing(MenuItem::getPrice));
        Collections.sort(menuItemsDescending, Comparator.comparing(MenuItem::getPrice).reversed());

        if(sortType.equalsIgnoreCase("ascending")) {
            for(MenuItem menuItem:menuItemsAscending) {
                responseList.add(new MenuItemResponse(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getImage(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        menuItem.isVegetarian()
                ));
            }
            MenuItemSpecailResponse menuItemSpecailResponse=new MenuItemSpecailResponse();
            menuItemSpecailResponse.setMenuItemResponses(responseList);
            return menuItemSpecailResponse;
        } else if (sortType.equalsIgnoreCase("descending")) {
            for (MenuItem menuItem : menuItemsDescending) {
                responseList.add(new MenuItemResponse(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getImage(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        menuItem.isVegetarian()
                ));
                MenuItemSpecailResponse menuItemSpecailResponse = new MenuItemSpecailResponse();
                menuItemSpecailResponse.setMenuItemResponses(responseList);
            }
            MenuItemSpecailResponse menuItemSpecailResponse=new MenuItemSpecailResponse();
            menuItemSpecailResponse.setMenuItemResponses(responseList);
            return menuItemSpecailResponse;
        }
        return null;
    }

    @Override
    public MenuItemVegeterian getSortByVegeterian() {
        List<MenuItem>menuItems = menuItemRepo.findAll();

        List<MenuItem> vegetarianItems = menuItems.stream()
                .filter(MenuItem::isVegetarian)
                .collect(Collectors.toList());

        List<MenuItem> nonVegetarianItems = menuItems.stream()
                .filter(item -> !item.isVegetarian())
                .collect(Collectors.toList());

        List<MenuItemResponse>menuItemResponsesVeg = new ArrayList<>();
        List<MenuItemResponse>menuItemResponsesNonVeg = new ArrayList<>();

        for(MenuItem menuItem:vegetarianItems){
            menuItemResponsesVeg.add(new MenuItemResponse(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getImage(),
                    menuItem.getPrice(),
                    menuItem.getDescription(),
                    menuItem.isVegetarian()
            ));
        }

        for(MenuItem menuItem:nonVegetarianItems){
            menuItemResponsesNonVeg.add(new MenuItemResponse(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getImage(),
                    menuItem.getPrice(),
                    menuItem.getDescription(),
                    menuItem.isVegetarian()
            ));
        }
        MenuItemVegeterian menuItemVegeterian = new MenuItemVegeterian();
        menuItemVegeterian.setVegeterians(menuItemResponsesVeg);
        menuItemVegeterian.setNonVegeterians(menuItemResponsesNonVeg);

        menuItemVegeterian.setNonName("NONVEGANS");
        menuItemVegeterian.setVeganName("VEGANS");
        return menuItemVegeterian;
    }
}
