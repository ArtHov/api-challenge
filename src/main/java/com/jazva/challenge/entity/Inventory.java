package com.jazva.challenge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity object corresponds to the Inventory.
 *
 * @author Artur
 */
@Entity
@Getter
@Setter
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String address;

    @Column
    private int count;

    @Column(name = "product_id")
    private Long productId;


}
