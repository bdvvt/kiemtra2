package com.example.duanlon2.security.principal;

import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
        return CustomUserDetails.builder()
                .user(user)
                .authorities(
                        user.getRoles().stream()
                                .map(role ->
                                        new SimpleGrantedAuthority(role.getRoleName().name()))
                                .toList()
                )
                .build();
    }
}
