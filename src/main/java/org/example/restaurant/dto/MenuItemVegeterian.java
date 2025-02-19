package org.example.restaurant.dto;

import java.util.List;

public class MenuItemVegeterian {
    private String VeganName;
    List<MenuItemResponse> vegeterians;
    private String NonName;
    List<MenuItemResponse> nonVegeterians;

    public String getVeganName() {
        return VeganName;
    }

    public void setVeganName(String veganName) {
        VeganName = veganName;
    }

    public String getNonName() {
        return NonName;
    }

    public void setNonName(String nonName) {
        NonName = nonName;
    }

    public List<MenuItemResponse> getVegeterians() {
        return vegeterians;
    }

    public void setVegeterians(List<MenuItemResponse> vegeterians) {
        this.vegeterians = vegeterians;
    }

    public List<MenuItemResponse> getNonVegeterians() {
        return nonVegeterians;
    }

    public void setNonVegeterians(List<MenuItemResponse> nonVegeterians) {
        this.nonVegeterians = nonVegeterians;
    }
}
