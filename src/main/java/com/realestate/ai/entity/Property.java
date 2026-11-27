package com.realestate.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="properties")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Property{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propertyType;

    private Integer bedrooms;

    private String city;

    private Double price;

    private String location;
}
