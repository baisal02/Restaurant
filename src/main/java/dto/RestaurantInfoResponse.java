package dto;

import entities.User;

import java.util.List;

public class RestaurantInfoResponse {
    private Long id;
    private int numberOfEmployees;
    private boolean haveOpenVacancies;
    private String location;
    private List<UserResponse> userResponses;
    private List<MenuItemResponse>menuItemResponses;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public boolean isHaveOpenVacancies() {
        return haveOpenVacancies;
    }

    public void setHaveOpenVacancies(boolean haveOpenVacancies) {
        this.haveOpenVacancies = haveOpenVacancies;
    }

    public List<UserResponse> getUserResponses() {
        return userResponses;
    }

    public void setUserResponses(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

    public List<MenuItemResponse> getMenuItemResponses() {
        return menuItemResponses;
    }

    public void setMenuItemResponses(List<MenuItemResponse> menuItemResponses) {
        this.menuItemResponses = menuItemResponses;
    }



}
