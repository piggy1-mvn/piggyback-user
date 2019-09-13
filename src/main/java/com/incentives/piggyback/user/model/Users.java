package com.incentives.piggyback.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.incentives.piggyback.user.util.customAnnotations.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name= "users",schema = "database1",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Size(min=2,max=30)
    @NotBlank(message = "First Name is mandatory")
    @Column(name="first_name")
    private String first_name;

    @Size(max=30)
    @Column(name="last_name")
    private String last_name;

    @Nullable
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
    @Column(name="email")
    private String email;

    @Column(name="user_interests")
    private String user_interests;

    @Role
    @NotBlank(message = "user_role is mandatory")
    @Column(name="user_role")
    private String user_role;

    @Column(name="user_type")
    private String user_type;

    @NotBlank(message = "device Id is mandatory")
    @Column(name="device_id")
    private String device_id;

    @Override
    public String toString() {
        return "User [userId=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", user_password="
                + user_password + ", mobile_number=" + mobile_number + ",mobile_verified" + mobile_verified + ",email " + email + ",user_interests " + user_interests
                + ",user_role" + user_type + ",device_id " + device_id +"]";
    }

}
