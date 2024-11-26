package com.hms.payload;

import com.hms.entity.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {

    private String roomType;
    private Double nightlyPrice;
    private Integer availableCount;
    private Property property;
}
