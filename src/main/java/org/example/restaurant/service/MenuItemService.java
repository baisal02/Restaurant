package org.example.restaurant.service;

import org.example.restaurant.dto.*;

public interface MenuItemService {
    SimpleResponse addMenuItem(MenuItemRequest menuItemRequest, Long restaurantId);
    MenuItemResponceCategory getMenuItemById(Long menuItemId);
    SimpleResponse updateMenuItem(MenuItemRequest menuItemRequest,Long menuItemId);
    SimpleResponse deleteMenuItem(Long menuItemId);

    MenuItemSpecailResponse getMenuItemWithFirstCharacter(String firstCharacter);
    MenuItemSpecailResponse getSorttedMenuItems(String sortType);
    MenuItemVegeterian getSortByVegeterian();
}
