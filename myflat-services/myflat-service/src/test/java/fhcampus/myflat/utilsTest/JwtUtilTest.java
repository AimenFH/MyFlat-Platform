
package fhcampus.myflat.utilsTest;

import fhcampus.myflat.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    @Test
    public void testJwtUtil() {
        JwtUtil jwtUtil = new JwtUtil();
        UserDetails userDetails = new User("testUser", "testPassword", Collections.emptyList());

        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), userDetails);

        assertEquals("testUser", jwtUtil.extractUserName(token));
        assertTrue(jwtUtil.isTokenValid(token, userDetails));
        assertFalse(jwtUtil.isTokenExpired(token));

        assertEquals("testUser", jwtUtil.extractUserName(refreshToken));
        assertTrue(jwtUtil.isTokenValid(refreshToken, userDetails));
        assertFalse(jwtUtil.isTokenExpired(refreshToken));
    }
}