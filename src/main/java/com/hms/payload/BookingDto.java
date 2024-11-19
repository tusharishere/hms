package com.hms.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDto {
    private Long id;
    private String guestName;
    private Double totalNights;
    private String mobile;
    private String email;
    private String roomType;
    private Double totalPrice;
    private String propertyName;
}
