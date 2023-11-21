package com.example.unimeeting.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.unimeeting.repository.UserRepository;
import com.example.unimeeting.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");

        User user = userRepository.findByUserId(user_id)
                .orElseThrow(IllegalArgumentException::new);
        System.out.println(user);
        return new MyUserDetails(user);
    }
}
