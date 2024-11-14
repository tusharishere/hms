package com.hms.repository;

import com.hms.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    @Query("SELECT b FROM Bookings b WHERE b.property.id = :propertyId AND " + "b.roomType = :roomType AND " + "((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate))")
    List<Bookings> findBookingsByPropertyIdAndDateRange(@Param("propertyId") long propertyId,
                                                        @Param("roomType") String roomType,
                                                        @Param("checkInDate") LocalDate checkInDate,
                                                        @Param("checkOutDate") LocalDate checkOutDate);
}