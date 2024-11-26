package com.hms.payload;

import com.hms.entity.Property;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDto {
    private String guestName;
    private String email;
    private String mobile;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Property property;
}
