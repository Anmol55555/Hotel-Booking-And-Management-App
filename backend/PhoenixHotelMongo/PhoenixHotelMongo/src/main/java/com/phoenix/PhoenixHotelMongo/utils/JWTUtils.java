package com.phoenix.PhoenixHotelMongo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtils {

    private static final  long EXPIRATION_TIME = 1000 * 60 * 24 * 7;  // expires in 7 days

    private final SecretKey Key;

    public JWTUtils() {
        // Dummy key
        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        // Hashing
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        // Hasing using HmacSHA256 algorithm and storing it to Key variable
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(this.Key)
                .compact();
    }

    // Extracts username from a given token
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    // Generic class type with return type T
    // This function will return the payload (data) of token
    // And with corresponds to what claimsTFunction value is requested, .apply() will return for that value
    // example, Claims::getSubject is requesting of username out of payload,  Claims::getExpiration is requesting the expiration data for the given token out of payload
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());

    }
    public boolean isValidToken(String  token, UserDetails userDetails){
        final String username = extractUsername((token));       // extracting username corresponding to the given token
        // will verify that the username of the given userDetails and username from the given token is matching or not
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}
