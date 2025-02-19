package org.example.restaurant.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class ChequeRequest {

    private LocalDate createdAt;

    private List<Long> foodIds;



    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<Long> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<Long> foodIds) {
        this.foodIds = foodIds;
    }
}
