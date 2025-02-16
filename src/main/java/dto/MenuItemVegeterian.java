package dto;

import java.util.List;

public class MenuItemVegeterian {
    List<MenuItemResponse> vegeterians;
    List<MenuItemResponse> nonVegeterians;

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
