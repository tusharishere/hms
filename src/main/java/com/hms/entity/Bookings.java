package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "check_in",nullable = false)
    private String checkInDate;

    @Column(name = "check_out",nullable = false)
    private String checkOutDate;

    @Column(name = "total_price",nullable = false)
    private Double totalPrice;

    @Column(name = "booking_status",nullable = false)
    private String bookingStatus;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "review_id")
    private Review review;

}