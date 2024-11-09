package com.hms.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ErrorDto {
    private String message;
//    private Date date;
//    private String uri;

    public ErrorDto(String message, Date date, String uri) {
        this.message = message;
//        this.date = date;
//        this.uri = uri;
    }
}
