package com.haydenshui.stock.utils;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.haydenshui.stock.config.JwtProperties;
import com.haydenshui.stock.securities.security.SecuritiesUserDetailsService;
import com.haydenshui.stock.securities.security.SecuritySecuritiesAccount;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtProperties jwtProperties;

    private final SecuritiesUserDetailsService securitiesUserDetailsService;

    public JwtAuthenticationFilter(JwtProperties jwtProperties, SecuritiesUserDetailsService securitiesUserDetailsService) {
        this.jwtProperties = jwtProperties;
        this.securitiesUserDetailsService = securitiesUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = JwtUtils.parseJWT(jwtProperties.getSecret(), token);
                String accountNumber = claims.get("accountNumber", String.class);
                String accountType = claims.get("accountType", String.class);
                String accNoAndType = accountNumber + ":" + accountType;

                SecuritySecuritiesAccount userDetails = securitiesUserDetailsService.loadUserByUsername(accNoAndType);

                UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | UsernameNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
