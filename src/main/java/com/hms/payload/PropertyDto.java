package com.hms.payload;

import com.hms.entity.City;
import com.hms.entity.Country;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {
    private String propertyName;
    private Integer no_of_guest;
    private Integer no_of_bedrooms;
    private Integer no_of_bathrooms;
    private Integer no_of_beds;
    private Country country;
    private City city;
}
