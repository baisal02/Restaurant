package org.example.restaurant.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="cheques")
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_gen")
    @SequenceGenerator(sequenceName = "cheque_seq", name = "cheque_gen",allocationSize = 1)
    private Long id;
    private Long priceAverage;
    private LocalDate date;

    @ManyToOne
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<MenuItem>menuItems;


    public void setUser(User user) {
        this.user = user;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public Cheque(Long priceAverage, LocalDate date) {
        this.priceAverage = priceAverage;
        this.date = date;
    }

    public Cheque() {
    }

    public Long getPriceAverage() {
        return priceAverage;
    }

    public void setPriceAverage(Long priceAverage) {
        this.priceAverage = priceAverage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
