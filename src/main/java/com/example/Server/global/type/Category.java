package com.example.Server.global.type;

import java.util.Arrays;

public enum Category {
    STUDY,      // 학업
    JOB,        // 직장
    FAMILY,     // 가족
    CAREER,     // 진로
    HEALTH,     // 건강
    RELATION,   // 관계
    FINANCE,    // 경제
    ETC;         //  기타

    public static boolean contains(Category category) {
        return Arrays.stream(Category.values()).anyMatch(v -> v.equals(category));
    }
}
