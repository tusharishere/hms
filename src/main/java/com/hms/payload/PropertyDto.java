package com.hms.payload;

import com.hms.entity.City;
import com.hms.entity.Country;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {
    private String propertyName;
    private Integer no_of_guests;
    private Integer no_of_rooms;
    private Integer no_of_bathrooms;
    private Integer no_of_beds;
    private City city;
    private Country country;
}
