package com.incentives.piggyback.user.model;

import javax.persistence.Column;
import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoles {

    @Column(name="user_role")
    private ArrayList<String> user_roles;

    @Override
    public String toString() {
        return "User [user_roles " + user_roles +"]";
    }
}
