package com.example.duanlon2.security.jwt;

import com.example.duanlon2.security.principal.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if(token != null) {
                if (tokenBlacklistService.isBlacklisted(token)) {
                    log.warn("Token is blacklisted (logged out)");
                    filterChain.doFilter(request, response);
                    return;
                }
                String username = jwtUtils.extractUsername(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if(jwtUtils.validationToken(token,userDetails)) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            log.error("Un Authentication {}", e.getMessage());
        }
        filterChain.doFilter(request,response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization!= null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}