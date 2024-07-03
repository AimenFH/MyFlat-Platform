package fhcampus.myflat.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT operations such as generating tokens, extracting information from tokens,
 * and validating tokens.
 */
@Component
public class JwtUtil {

    /**
     * Extracts the username from the JWT token.
     *
     * @param token JWT token from which the username is to be extracted.
     * @return String The username extracted from the token.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a new JWT token for the given UserDetails.
     *
     * @param userDetails UserDetails object containing information about the user.
     * @return String A new JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Validates the JWT token against the UserDetails.
     * Checks if the token is valid and not expired for the given user details.
     *
     * @param token JWT token to validate.
     * @param userDetails UserDetails to validate against the token.
     * @return boolean True if the token is valid and not expired, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extracts a claim from the JWT token.
     *
     * @param token JWT token from which the claim is to be extracted.
     * @param claimsResolvers Function to process the Claims object and extract the required information.
     * @return <T> The extracted claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Generates a new JWT token with additional claims.
     *
     * @param extraClaims Additional claims to include in the token.
     * @param userDetails UserDetails object containing information about the user.
     * @return String A new JWT token with the additional claims.
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Generates a new refresh JWT token with additional claims.
     *
     * @param extraClaims Additional claims to include in the refresh token.
     * @param userDetails UserDetails object containing information about the user.
     * @return String A new refresh JWT token with the additional claims.
     */
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token JWT token to check for expiration.
     * @return boolean True if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token JWT token from which the expiration date is to be extracted.
     * @return Date The expiration date of the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token JWT token from which the claims are to be extracted.
     * @return Claims The claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key for the JWT token.
     *
     * @return Key The signing key used for JWT token generation and validation.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(keyBytes);
    }

}