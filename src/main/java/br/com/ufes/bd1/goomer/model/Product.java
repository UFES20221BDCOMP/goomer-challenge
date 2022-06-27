package br.com.ufes.bd1.goomer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter @Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "sale_id")
    private ProductSale sale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (!(o instanceof Product)) return false;
        
        Product product = (Product) o;
        
        return Objects.equals(getId(), product.getId()) && 
                Objects.equals(getDescription(), product.getDescription()) && 
                Objects.equals(getPrice(), product.getPrice()) && 
                Objects.equals(getImagePath(), product.getImagePath()) &&
                Objects.equals(getProductCategory(), product.getProductCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getPrice(), getImagePath());
    }
}
