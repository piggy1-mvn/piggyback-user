package com.incentives.piggyback.user.util.constants;

import lombok.NoArgsConstructor;
import java.util.ArrayList;

@NoArgsConstructor
public enum Roles {
    PIGGY_ADMIN,
    PIGGY_USER,
    PARTNER_ADMIN,
    PARTNER_USER,
    PARTNER_API_USER,
    USER_TYPE_FB;

        public static ArrayList<String> getAllRoles() {
            Roles[] roles = Roles.values();
            ArrayList<String> stringRole = new ArrayList<>();
            for (Roles role : roles) {
                stringRole.add(role.toString());
            }
            return stringRole;
        }
    }
