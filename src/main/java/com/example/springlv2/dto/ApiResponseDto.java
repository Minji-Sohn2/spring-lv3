package com.example.springlv2.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {

    private String message;
    private int statusCode;

    public ApiResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}
