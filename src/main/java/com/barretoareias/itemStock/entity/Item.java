package com.barretoareias.itemStock.entity;

import com.barretoareias.itemStock.enums.Packing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private int max;

    @Column(nullable = false)
    private int quantity;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date addedDete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Packing type;

    @Column(nullable = false)
    private Long personId;
}
