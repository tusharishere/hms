package com.hms.repository;

import com.hms.entity.Images;
import com.hms.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {

  List<Images> findByProperty(Property property);
}