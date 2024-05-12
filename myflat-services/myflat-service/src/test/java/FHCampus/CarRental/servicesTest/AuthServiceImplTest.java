package FHCampus.CarRental.servicesTest;

import FHCampus.CarRental.dtos.SignupRequest;
import FHCampus.CarRental.entities.Users;
import FHCampus.CarRental.enums.UserRole;
import FHCampus.CarRental.repositories.UserRepository;
import FHCampus.CarRental.services.auth.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAdminAccount() {
        when(userRepository.findByUserRole(UserRole.ADMIN)).thenReturn(null);
        when(bCryptPasswordEncoder.encode("adminadmin")).thenReturn("encryptedPassword");
        authService.createAdminAccount();
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testCreateAdminAccount_existingAdmin() {
        Users admin = new Users();
        when(userRepository.findByUserRole(UserRole.ADMIN)).thenReturn(admin);
        authService.createAdminAccount();
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void testCreateCustomer() {
        SignupRequest signupRequest = new SignupRequest("test@example.com", "TestUser", "password");
        Users user = new Users();
        user.setId(1L);

        when(bCryptPasswordEncoder.encode("password")).thenReturn("encryptedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(user);

        var result = authService.createCustomer(signupRequest);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testHasCustomerWithEmail() {
        when(userRepository.findFirstByEmail("test@example.com")).thenReturn(java.util.Optional.of(new Users()));
        assertTrue(authService.hasCustomerWithEmail("test@example.com"));
    }
}
