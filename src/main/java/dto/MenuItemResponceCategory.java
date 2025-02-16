package dto;

public class MenuItemResponceCategory {
    private String categoryName;
    private MenuItemResponse menuItemResponse;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public MenuItemResponse getMenuItemResponse() {
        return menuItemResponse;
    }

    public void setMenuItemResponse(MenuItemResponse menuItemResponse) {
        this.menuItemResponse = menuItemResponse;
    }
}
