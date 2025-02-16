package entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_gen")
    @SequenceGenerator(sequenceName = "category_seq", name = "category_gen",allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Subcategory>subcategories;


    public Long getId() {
        return id;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
