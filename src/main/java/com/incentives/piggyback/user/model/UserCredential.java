package com.incentives.piggyback.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserCredential {

    @NotBlank(message= "Username is mandatory")
    @Column(name="email")
    private  String email;

    @NotBlank(message= "Password is mandatory")
    @Column(name="user_password")
    private  String user_password;
}
