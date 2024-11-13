package com.hms.controller;

import com.hms.entity.Bookings;
import com.hms.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {


    private BookingService bookingsService;

    public BookingController(BookingService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    public ResponseEntity<Bookings> createBooking(@RequestBody Bookings booking) {
        Bookings savedBooking = bookingsService.saveBooking(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookings> updateBooking(@PathVariable Long id, @RequestBody Bookings booking) {
        Bookings updatedBooking = bookingsService.updateBooking(id, booking);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingsService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookings> getBookingById(@PathVariable Long id) {
        Bookings booking = bookingsService.getBookingById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Bookings>> getAllBookings() {
        List<Bookings> bookings = bookingsService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
