package com.hms.repository;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.Images;
import com.hms.entity.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ImagesRepositoryTest {

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    private Property property;
    private Images image1;
    private Images image2;

    @BeforeEach
    void setUp() {
        // Create and save Country
        Country country = new Country();
        country.setCountryName("Test Country");
        country = countryRepository.save(country);

        // Create and save City
        City city = new City();
        city.setCityName("Test City");
        city = cityRepository.save(city);

        // Create and save Property
        property = new Property();
        property.setPropertyName("Test Property");
        property.setNo_of_guest(4);
        property.setNo_of_bedrooms(2);
        property.setNo_of_bathrooms(1);
        property.setNo_of_beds(2);
        property.setCountry(country);
        property.setCity(city);
        property = propertyRepository.save(property);

        // Create and save Images
        image1 = new Images();
        image1.setUrl("http://example.com/image1.jpg");
        image1.setProperty(property);

        image2 = new Images();
        image2.setUrl("http://example.com/image2.jpg");
        image2.setProperty(property);

        imagesRepository.save(image1);
        imagesRepository.save(image2);
    }

    @AfterEach
    void tearDown() {
        imagesRepository.deleteAll();
        propertyRepository.deleteAll();
        countryRepository.deleteAll();
        cityRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void testFindByProperty_Found() {
        List<Images> images = imagesRepository.findByProperty(property);
        assertThat(images).hasSize(2);
        assertThat(images).contains(image1, image2);
    }
}
