package com.raquibul.bank.payment.rest.config;

import com.raquibul.bank.payment.rest.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

/**
 * The provider class which generates the tokens.
 */
@Component
public class JwtTokenProvider implements Serializable {
    private static final String CLAIMS_AUTH = "auth";
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKeyForToken;

    @Value("${jwt.token-invalidate-time}")
    private long validityMilliSeconds;


    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKeyForToken = Base64.getEncoder().encodeToString(secretKeyForToken.getBytes());
    }

    /**
     * This method creates a JWT token based on the provided user and role information
     * @param username
     * @param role
     * @return
     */
    public String createToken(String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(CLAIMS_AUTH, role);
        Date now = new Date();

        return Jwts.builder().setClaims(claims).setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMilliSeconds))
                .signWith(SignatureAlgorithm.HS256, secretKeyForToken).compact();
    }

    /**
     * Method which creates the {@link Authentication} instance using userDetailsService
     * @param username userName
     * @return the Authentication instance
     */
    public Authentication getAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKeyForToken).parseClaimsJws(token).getBody();
    }

}
