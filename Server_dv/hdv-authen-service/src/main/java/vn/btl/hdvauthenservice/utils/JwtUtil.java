package vn.btl.hdvauthenservice.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.btl.hdvauthenservice.exceptions.JwtTokenMalformedException;
import vn.btl.hdvauthenservice.exceptions.JwtTokenMissingException;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;


    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateToken(String id, Collection<String> authorityRoles, Long issuedAtTimestamp, Long expiredTimestamp, Map<String, Object> info) {
        Date iss = new Date(issuedAtTimestamp);
        Date exp = new Date(expiredTimestamp);
        return Jwts.builder()
                .setClaims(info)
                .setSubject(id)
                .setIssuedAt(iss)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

    }

    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }
}
