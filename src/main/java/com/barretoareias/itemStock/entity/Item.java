package com.barretoareias.itemStock.entity;

import com.barretoareias.itemStock.enums.Packing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

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

    @Column(nullable = false)
    private LocalDate addedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Packing type;
    
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person personId;
}
