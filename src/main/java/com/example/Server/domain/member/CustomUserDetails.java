package com.example.Server.domain.member;

import com.example.Server.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;  // ★ 로그인한 실제 Member 엔티티

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // MEMBER.role = "USER" 형태로 들어있다고 가정
        return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
    }

    @Override
    public String getPassword() {
        // 소셜 로그인이라 패스워드 없음
        return null;
    }

    @Override
    public String getUsername() {
        // 스프링은 username = unique key 로 사용함 → socialId 사용
        return member.getSocialId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 없음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 없음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // credential 만료 없음
    }

    @Override
    public boolean isEnabled() {
        return true; // 항상 활성화 상태
    }
}

