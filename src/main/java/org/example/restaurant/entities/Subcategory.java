package org.example.restaurant.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subcategories")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_gen")
    @SequenceGenerator(sequenceName = "subcategory_seq", name = "subcategory_gen",allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "subcategory",cascade = CascadeType.PERSIST)
    private List<MenuItem>menuItems;

    @ManyToOne
    private Category category;



    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public Category getCategory() {
        return category;
    }

    public Subcategory(String name) {
        this.name = name;
    }

    public Subcategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
