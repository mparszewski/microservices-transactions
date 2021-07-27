package com.mparszewski.order.model;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name = "order_table")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    private String status;

    @Column(name = "modifications")
    @OneToMany(mappedBy = "id")
    private List<Modification> modifications;
}
