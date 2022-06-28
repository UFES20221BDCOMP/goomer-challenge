package br.com.ufes.bd1.goomer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@Entity
@Table(name = "product_sale")
public class ProductSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany
    @JoinTable(
            name = "sale_validity_period",
            joinColumns = @JoinColumn(name = "sale_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "timespan_id", unique = true, nullable = false))
    private List<Timespan> saleValidityPeriods;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (!(o instanceof ProductSale)) return false;
        
        ProductSale that = (ProductSale) o;
        
        return Objects.equals(getDescription(), that.getDescription()) && 
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getSaleValidityPeriods(), that.getSaleValidityPeriods());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getPrice());
    }
}
