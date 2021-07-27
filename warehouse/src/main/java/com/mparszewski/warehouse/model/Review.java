package com.mparszewski.warehouse.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "review")
@Data
public class Review {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating;

    @ManyToOne(fetch = EAGER)
    private Product product;

}
