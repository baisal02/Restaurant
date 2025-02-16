package dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class ChequeRequest {
    private LocalDate createdAt;
    private List<String> foodNames;

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getFoodNames() {
        return foodNames;
    }

    public void setFoodNames(List<String> foodNames) {
        this.foodNames = foodNames;
    }
}
