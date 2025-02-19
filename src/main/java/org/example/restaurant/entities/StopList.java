package org.example.restaurant.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "stoplists")
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stoplist_gen")
    @SequenceGenerator(sequenceName = "stoplist_seq", name = "stoplist_gen",allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;

    @OneToOne(mappedBy = "stoplist")
    private MenuItem menuItem;


    public StopList(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public StopList() {
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
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
