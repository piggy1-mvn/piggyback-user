package com.incentives.piggyback.user.util;

import lombok.NoArgsConstructor;
import java.util.ArrayList;

@NoArgsConstructor
public enum Roles {
    PIGGY_ADMIN,
    PIGGY_USER,
    PARTNER_ADMIN,
    PARTNER_USER,
    PARTNER_API_USER;

        public static ArrayList<String> getAllRoles() {
            Roles[] roles = Roles.values();
            ArrayList<String> stringRole = new ArrayList<String>();
            for (Roles role : roles) {
                stringRole.add(role.toString());
            }
            return stringRole;
        }
    }
