package com.hms.service;

import com.hms.entity.Bookings;
import com.hms.repository.BookingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private BookingsRepository bookingsRepository;

    public BookingService(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }


    public Bookings saveBooking(Bookings booking) {
        return bookingsRepository.save(booking);
    }


    public Bookings updateBooking(Long id, Bookings booking) {
        booking.setId(id);
        return bookingsRepository.save(booking);
    }


    public void deleteBooking(Long id) {
        bookingsRepository.deleteById(id);
    }


    public Bookings getBookingById(Long id) {
        return bookingsRepository.findById(id).orElse(null);
    }


    public List<Bookings> getAllBookings() {
        return bookingsRepository.findAll();
    }
}
