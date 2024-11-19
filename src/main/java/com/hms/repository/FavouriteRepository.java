package com.hms.repository;

import com.hms.entity.AppUser;
import com.hms.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @Query("select f from Favourite f where f.appUser=:user")
    List<Favourite> fetchByUsername(@Param("user") AppUser user);
}