package com.raquibul.bank.payment.rest.config;

import com.raquibul.bank.payment.rest.model.Error;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * JWT Token filer which intercepts the requests and validates the JWT token
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenProvider tokenProvider;

    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("doFilterInternal : method invoked");
        String token = request.getHeader("Authorization");
        if (token != null) {
            log.info("Token found in header");
            try {
                Claims claims = tokenProvider.getClaimsFromToken(token);
                if (!claims.getExpiration().before(new Date())) {
                    Authentication authentication = tokenProvider.getAuthentication(claims.getSubject());
                    if (authentication.isAuthenticated()) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Token is validated");
                    }
                }
            } catch (RuntimeException e) {
                log.error("There was some error in validating the token");
                SecurityContextHolder.clearContext();
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println(Error.INVALID_TOKEN_ERROR.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
