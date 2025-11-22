package com.example.Server.global.util;

import com.example.Server.domain.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MemberUtil {

    public static Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        // principal이 Member 객체라면 반환
        if (principal instanceof Member member) {
            return member;
        }

        return null;
    }
}
