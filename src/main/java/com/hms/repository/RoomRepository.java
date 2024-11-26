package com.hms.repository;

import com.hms.entity.Property;
import com.hms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.date >= :startDate AND r.date < :endDate AND r.roomType = :roomType AND r.property = :property ")
    List<Room> findRoomsByTypeAndProperty(
            @Param("startDate") LocalDate fromDate,
            @Param("endDate") LocalDate toDate,
            @Param("roomType") String roomType,
            @Param("property") Property property
    );

}