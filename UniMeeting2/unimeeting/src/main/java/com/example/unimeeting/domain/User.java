package com.example.unimeeting.domain;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "user_id", nullable = false,unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String category;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String role;

    @Builder
    public User(Integer idx, String userId, String password, String nickname, String email, String category, String phoneNumber, String role) {
        this.idx = idx;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.role = "USER";

    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
    public static User createUser(String userId, String password, PasswordEncoder passwordEncoder, String nickname, String email, String category, String phoneNumber, String role) {
        return new User(null, userId, passwordEncoder.encode(password), nickname, email, category, phoneNumber, role);
    }
}

