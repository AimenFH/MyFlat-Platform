package fhcampus.myflat.servicesTest;

import fhcampus.myflat.entities.User;
import fhcampus.myflat.repositories.UserRepository;
import fhcampus.myflat.servicesTest.jwt.JwtUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class JwtUserServiceImplTest {

    @InjectMocks
    JwtUserServiceImpl jwtUserService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserDetailsService() {
        User user = new User();
        user.setEmail("test@test.com");

        when(userRepository.findFirstByEmail("test@test.com")).thenReturn(Optional.of(user));

        UserDetailsService userDetailsService = jwtUserService.userDetailsService();
        assertEquals(user, userDetailsService.loadUserByUsername("test@test.com"));
    }

    @Test
    public void testUserDetailsServiceThrowsUsernameNotFoundException() {
        when(userRepository.findFirstByEmail("test@test.com")).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = jwtUserService.userDetailsService();
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("test@test.com"));
    }
}