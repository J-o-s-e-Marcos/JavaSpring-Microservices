package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID userid;
    private String username;
    private String email;
    private String password;
    private String oldPasswod;
    private String fullName;
    private String cpf;
    private String imageUrl;

}
