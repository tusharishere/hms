package com.hms.service;

import com.hms.repository.BookingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class BookingService {
    private BookingsRepository bookingsRepository;

    public BookingService(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @Transactional
    public boolean checkRoomAvailability(long propertyId, String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        boolean isRoomAvailable = bookingsRepository.findBookingsByPropertyIdAndDateRange(propertyId,roomType ,checkInDate, checkOutDate).isEmpty();
        return isRoomAvailable;
    }
}
