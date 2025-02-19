package org.example.restaurant.dto;

import java.util.List;

public class MenuItemSpecailResponse {
    private List<MenuItemResponse>menuItemResponses;


    public List<MenuItemResponse> getMenuItemResponses() {
        return menuItemResponses;
    }

    public void setMenuItemResponses(List<MenuItemResponse> menuItemResponses) {
        this.menuItemResponses = menuItemResponses;
    }

    public MenuItemSpecailResponse() {
    }

    public MenuItemSpecailResponse(List<MenuItemResponse> menuItemResponses) {
        this.menuItemResponses = menuItemResponses;
    }
}
