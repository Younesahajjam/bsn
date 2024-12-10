package com.koki.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @Email(message = "Email is not formatted")
    @NotEmpty(message = "email is mondatory")
    @NotBlank(message = "email is mondatory")
    private String email;
    @NotEmpty(message = "password is mondatory")
    @NotBlank(message = "password is mondatory")
    @Size(min = 8,message = "password should be 8 charactere long mininimum")
    private String password;
}
