package com.hms.payload;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Integer rating;
    private String description;
    private AppUser appUser;
    private Property property;
}