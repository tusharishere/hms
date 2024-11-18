package com.hms.repository;

import com.hms.entity.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setCountryName("TestCountry");
        countryRepository.save(country);
    }

    @AfterEach
    void tearDown() {
        country = null;
        countryRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void testFindByCountryName_Found() {
        Country foundCountry = countryRepository.findByCountryName(country.getCountryName()).orElse(null);
        assertThat(foundCountry).isNotNull();
        assertThat(foundCountry.getCountryName()).isEqualTo(country.getCountryName());
    }

    // Test case FAIL
    @Test
    void testFindByCountryName_Not_Found() {
        Country foundCountry = countryRepository.findByCountryName("NonExistingCountry").orElse(null);
        assertThat(foundCountry).isNull();
    }
}