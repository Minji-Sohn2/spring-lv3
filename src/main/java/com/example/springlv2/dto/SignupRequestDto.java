package com.example.springlv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Size(min = 4, max = 10, message = "4자 이상, 10자 이하")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a~z), 숫자(0~9)")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15, message = "8자 이상, 15자 이하")
    @Pattern(regexp = "^[A-Za-z0-9`~!@#$%^&*()-_=+]*$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자(`~!@#$%^&*()-_=+)")
    private String password;
    private boolean admin = false;
    private String adminToken = "";

}