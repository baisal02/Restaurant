package org.example.restaurant.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_gen")
    @SequenceGenerator(sequenceName = "restaurant_seq", name = "restaurant_gen",allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private BigDecimal service;

    @OneToMany(mappedBy = "restaurant",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<User> userList;

    @OneToMany(mappedBy = "restaurant",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<MenuItem> menuItemList;


    public Long getId() {
        return id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public Restaurant(String name, String location, String restType, int numberOfEmployees, BigDecimal service) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }

    public Restaurant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRestType() {
        return restType;
    }

    public void setRestType(String restType) {
        this.restType = restType;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public BigDecimal getService() {
        return service;
    }

    public void addChequePrice(Long value) {
        this.service = this.service.add(BigDecimal.valueOf(value));
    }
}
