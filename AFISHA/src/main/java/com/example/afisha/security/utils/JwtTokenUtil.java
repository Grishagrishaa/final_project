package com.example.afisha.security.utils;

import com.example.afisha.service.EventService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtTokenUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String jwtSecret = "IwANTtObECOMEaDEVELOPERIwANTtObECOMEaDEVELOPERIW";

    private static final String jwtIssuer = "GRIGORY_DMITRIEVICH";

    public static String generateAccessToken(String nick){
        return Jwts.builder()
                .setSubject(nick)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.info("SignatureException");
            throw new IllegalArgumentException("Exception indicating that either calculating a signature or verifying an existing signature of a JWT failed.");
        } catch (MalformedJwtException ex) {
            log.info("MalformedJwtException");
            throw new IllegalArgumentException("Exception indicating that a JWT was not correctly constructed and should be rejected.");
        } catch (ExpiredJwtException ex) {
            log.info("ExpiredJwtException");
            throw new IllegalArgumentException("Exception indicating that a JWT was accepted after it expired and must be rejected.");
        } catch (UnsupportedJwtException ex) {
            log.info("UnsupportedJwtException");
            throw new IllegalArgumentException("Exception thrown when receiving a JWT in a particular format/configuration that does not match the format expected by the application.");
        } catch (IllegalArgumentException ex) {
            log.info("IllegalArgumentException");
            throw new IllegalArgumentException("IllegalArgumentException JWT");
        }
    }
}
