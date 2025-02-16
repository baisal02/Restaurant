package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StopListResponse {
    private Long id;

    private String reason;
    private LocalDate date;

    private MenuItemResponse menuItemResponse;

    public MenuItemResponse getMenuItemResponse() {
        return menuItemResponse;
    }

    public void setMenuItemResponse(MenuItemResponse menuItemResponse) {
        this.menuItemResponse = menuItemResponse;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
