package dto;

import java.time.LocalDate;

public class StopListRequest {
    private String reason;
    private LocalDate date;
    private MenuItemRequest menuItem;




    public MenuItemRequest getMenuItem() {
        return menuItem;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setMenuItem(MenuItemRequest menuItem) {
        this.menuItem = menuItem;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
