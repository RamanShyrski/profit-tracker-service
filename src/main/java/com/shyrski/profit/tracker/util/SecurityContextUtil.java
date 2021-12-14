package com.shyrski.profit.tracker.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    public static String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
