package com.hms.repository;

import com.hms.entity.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setCityName("TestCity");
        cityRepository.save(city);
    }

    @AfterEach
    void tearDown() {
        city = null;
        cityRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void testFindByCityName_Found() {
        City foundCity = cityRepository.findByCityName(city.getCityName()).orElse(null);
        assertThat(foundCity).isNotNull();
        assertThat(foundCity.getCityName()).isEqualTo(city.getCityName());
    }

    // Test case FAIL
    @Test
    void testFindByCityName_Not_Found() {
        City foundCity = cityRepository.findByCityName("NonExistingCity").orElse(null);
        assertThat(foundCity).isNull();
    }
}