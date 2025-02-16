package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="menuitems")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menuitem_gen")
    @SequenceGenerator(sequenceName = "menuitem_seq", name = "menuitem_gen",allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private int price;
    private String description;
    @Column(name = "is_vegetarian")
    private boolean isVegetarian;

    @OneToOne(cascade = {CascadeType.REMOVE})
    private StopList stoplist;

    @ManyToOne
    private Subcategory subcategory;

    @ManyToMany(mappedBy = "menuItems")
    private List<Cheque> cheques;

    @ManyToOne
    private Restaurant restaurant;


    public Long getId() {
        return id;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public StopList getStoplist() {
        return stoplist;
    }

    public void setStoplist(StopList stoplist) {
        this.stoplist = stoplist;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public MenuItem() {
    }

    public MenuItem(String name, String image, int price, String description, boolean isVegetarian) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
