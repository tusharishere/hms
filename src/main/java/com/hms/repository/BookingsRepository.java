package com.hms.repository;

import com.hms.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
}