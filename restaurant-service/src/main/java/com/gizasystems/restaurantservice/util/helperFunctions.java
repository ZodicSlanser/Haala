package com.gizasystems.restaurantservice.util;

import jakarta.servlet.http.HttpServletRequest;

public class helperFunctions {
    public static Long getUserId(HttpServletRequest request) {
      try {
        String userIdHeader = request.getHeader("X-User-Id");
        if (userIdHeader != null && !userIdHeader.isEmpty()) {
          return Long.parseLong(userIdHeader);
        }
      } catch (Exception e) { }
      return null;
    }

    public static String getUserRole(HttpServletRequest request) {
      try {
        String userRoleHeader = request.getHeader("X-User-Role");
        if (userRoleHeader != null && !userRoleHeader.isEmpty()) {
          return userRoleHeader;
        }
      } catch (Exception e) { }
      return null;
    }
}
