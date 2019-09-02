package com.incentives.piggyback.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "userdb", schema = "database1")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Size(min=2,max=30)
    @NotBlank(message = "First Name is mandatory")
    private String first_name;

    @Size(max=30)
    @Column(name="last_name")
    private String last_name;

    @NotBlank(message = "Password is mandatory")
    @Column(name="user_password")
    private String user_password;

    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\\\s\\\\./0-9]*$")
    @NotBlank(message = "Mobile Number is mandatory")
    @Column(name="mobile_number")
    private String mobile_number;

    @Column(name="mobile_verified")
    private Boolean mobile_verified;

    @Email
    @NotBlank(message = "User email is mandatory")
    @Column(name="user_email")
    private String user_email;

    @Column(name="user_interests")
    private String user_interests;

    @Column(name="user_role")
    private Boolean user_role;

    @Column(name="user_type")
    private String user_type;

    @NotBlank(message = "device Id is mandatory")
    @Column(name="device_id")
    private String device_id;

}