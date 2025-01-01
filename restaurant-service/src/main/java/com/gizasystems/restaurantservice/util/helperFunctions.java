package com.gizasystems.restaurantservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class helperFunctions {
    public static Long getOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long OwnerId = null;
        try {
            OwnerId = Long.parseLong(authentication.getName());
        } catch (Exception e) {
            return null;
        }
        return OwnerId;
    }

}
