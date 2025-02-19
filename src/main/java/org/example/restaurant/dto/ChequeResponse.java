package org.example.restaurant.dto;

import java.time.LocalDate;
import java.util.List;

public class ChequeResponse {
    private Long chequeId;
    private Long priceAverage;
    private LocalDate createdAt;
    private List<MenuItemResponse>menuItemResponses;








    public ChequeResponse() {
    }

    public ChequeResponse(Long chequeId, Long priceAverage, LocalDate createdAt) {
        this.chequeId = chequeId;
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
    }

    public Long getChequeId() {
        return chequeId;
    }

    public void setChequeId(Long chequeId) {
        this.chequeId = chequeId;
    }

    public Long getPriceAverage() {
        return priceAverage;
    }

    public void setPriceAverage(Long priceAverage) {
        this.priceAverage = priceAverage;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<MenuItemResponse> getMenuItemResponses() {
        return menuItemResponses;
    }

    public void setMenuItemResponses(List<MenuItemResponse> menuItemResponses) {
        this.menuItemResponses = menuItemResponses;
    }
}
