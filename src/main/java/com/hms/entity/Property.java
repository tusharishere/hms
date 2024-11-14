package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String propertyName;

    @Column(name = "no_of_guest", nullable = false)
    private Integer no_of_guest;

    @Column(name = "no_of_bedrooms", nullable = false)
    private Integer no_of_bedrooms;

    @Column(name = "no_of_bathrooms", nullable = false)
    private Integer no_of_bathrooms;

    @Column(name = "no_of_beds", nullable = false)
    private Integer no_of_beds;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "country_id")
    private Country country;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "city_id")
    private City city;

}