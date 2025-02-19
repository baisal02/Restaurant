package org.example.restaurant.dto;

import java.util.List;

public class SubCategoryRequest {


    private String name;
    private List<MenuItemRequest>menuItemsRequestList;


    public SubCategoryRequest(String name) {
        this.name = name;
    }


    public List<MenuItemRequest> getMenuItemsRequestList() {
        return menuItemsRequestList;
    }

    public void setMenuItemsRequestList(List<MenuItemRequest> menuItemsRequestList) {
        this.menuItemsRequestList = menuItemsRequestList;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
