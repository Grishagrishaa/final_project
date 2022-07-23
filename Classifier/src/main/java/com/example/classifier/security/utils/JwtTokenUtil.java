package com.example.classifier.security.utils;

import io.jsonwebtoken.*;

public class JwtTokenUtil {

    private static final String jwtSecret = "IwANTtObECOMEaDEVELOPERIwANTtObECOMEaDEVELOPERIW";


    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new IllegalArgumentException("Exception indicating that either calculating a signature or verifying an existing signature of a JWT failed.");
        } catch (MalformedJwtException ex) {
            throw new IllegalArgumentException("Exception indicating that a JWT was not correctly constructed and should be rejected.");
        } catch (ExpiredJwtException ex) {
            throw new IllegalArgumentException("Exception indicating that a JWT was accepted after it expired and must be rejected.");
        } catch (UnsupportedJwtException ex) {
            throw new IllegalArgumentException("Exception thrown when receiving a JWT in a particular format/configuration that does not match the format expected by the application.");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("IllegalArgumentException JWT");
        }
    }
}
