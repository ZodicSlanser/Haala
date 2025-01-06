package com.gizasystems.restaurantservice.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Map<String, String> details;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }


}
