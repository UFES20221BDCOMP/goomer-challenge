package br.com.ufes.bd1.goomer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "address")
public class Address {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;
}
