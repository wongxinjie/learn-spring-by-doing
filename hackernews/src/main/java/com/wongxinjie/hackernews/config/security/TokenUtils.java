package com.wongxinjie.hackernews.config.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenUtils implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private static final long serialVersionUID = 1L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration")
    private Long expiration;


    public String getUsernameFromToken(String token) {
        String username = null;

        final Claims claims = getClaimsFromToken(token);
        if(claims != null) {
            username = claims.getSubject();
        }

        return username;
    }

    private Date getCreatedDateFfromToken(String token) {
        Date created = null;

        final Claims claims = getClaimsFromToken(token);
        if(claims != null) {
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        }
        return created;
    }

    private Date getExpirationDateFromToken(String token){
        Date expiration = null;

        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            expiration = claims.getExpiration();
        }

        return expiration;
    }

    private Claims getClaimsFromToken(String token){
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            claims = null;
        }

        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public String getRefreshToken(String token) {
        String refreshedToken = null;

        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        }
        return refreshedToken;
    }

    Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
