package fhcampus.myflat.servicesTest;

import fhcampus.myflat.entities.User;
import fhcampus.myflat.servicesTest.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    SecurityContext securityContext;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCurrentUser() {
        User user = new User();
        user.setEmail("test@test.com");

        when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        SecurityContextHolder.setContext(securityContext);

        User currentUser = userService.getCurrentUser();

        assertEquals("test@test.com", currentUser.getEmail());
    }
}