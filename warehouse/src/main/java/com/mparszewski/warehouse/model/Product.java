package com.mparszewski.warehouse.model;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "available")
    private boolean available;

    @OneToMany(mappedBy = "id", fetch = LAZY)
    private List<Review> reviews;

}
