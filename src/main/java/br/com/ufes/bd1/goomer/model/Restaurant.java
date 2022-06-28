package br.com.ufes.bd1.goomer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private Address address;

    @OneToMany
    @JoinTable(
            name = "restaurant_business_hours",
            joinColumns = @JoinColumn(name = "restaurant_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "timespan_id", unique = true, nullable = false))
    private List<Timespan> businessHours;


    @Transient
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products;
}
