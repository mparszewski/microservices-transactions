package com.mparszewski.order.model;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.*;

@Data
@Table(name = "modification")
@Entity
public class Modification {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "modification_date")
    private Date modificationDate;

    @ManyToOne
    private Order order;
}
