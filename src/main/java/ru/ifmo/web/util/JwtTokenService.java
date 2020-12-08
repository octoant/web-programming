package ru.ifmo.web.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * The class supports for generation new token value and getting information
 * about exiting.
 *
 * @author Bobur Zakirov
 * @since 12/8/2020 11:07:13
 */
@Service
public class JwtTokenService {

      @Value("${spring.jwt.authorization.secret-key}")
      private String secretKey;

      @Value("${spring.jwt.authorization.expiration-millis}")
      private int expirationMillis;

      public String generateNewToken(UserDetails userDetails) {
            return creatNewToken(userDetails.getUsername());
      }

      private String creatNewToken(String subject) {
            return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                   .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                   .compact();
      }
      
      public Boolean validateToken(String jwtToken, UserDetails userDetails) {
            String username = extractUsername(jwtToken);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
      }

      private Boolean isTokenExpired(String jwtToken) {
            return extractExpireDate(jwtToken).after(new Date());
      }

      public Claims extractAllClaims(String jwtToken) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
      }

      public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
            Claims claims = extractAllClaims(jwtToken);
            return claimResolver.apply(claims);
      }

      public String extractUsername(String jwtToken) {
            return extractClaim(jwtToken, Claims::getSubject);
      }

      public Date extractExpireDate(String jwtToken) {
            return extractClaim(jwtToken, Claims::getExpiration);
      }
}
