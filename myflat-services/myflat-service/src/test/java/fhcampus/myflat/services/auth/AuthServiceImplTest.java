package fhcampus.myflat.services.auth;

import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.enums.UserRole;
import fhcampus.myflat.exceptions.EmailAlreadyExistsException;
import fhcampus.myflat.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTenantCreatesTenantWhenEmailDoesNotExist() {
        SignupRequest signupRequest = new SignupRequest("nonExistingEmail@test.com", "Tenant",
                "tenantpassword", "123456789");

        User mockUser = new User(1L, signupRequest.getName(), signupRequest.getEmail(),
                new BCryptPasswordEncoder().encode(signupRequest.getPassword()), UserRole.TENANT,
                signupRequest.getPhoneNumber());

        when(userRepository.findFirstByEmail(signupRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserDto result = authService.createTenant(signupRequest);

        assertNotNull(result);
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertEquals(mockUser.getPassword(), result.getPassword());
        assertEquals(mockUser.getUserRole(), result.getUserRole());
        assertEquals(mockUser.getPhoneNumber(), result.getPhoneNumber());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createTenantThrowsExceptionWhenEmailExists() {
        SignupRequest signupRequest = new SignupRequest("existingEmail@test.com", "Tenant",
                "tenantpassword", "123456789");

        when(userRepository.findFirstByEmail(signupRequest.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> authService.createTenant(signupRequest));
    }

    @Test
    void createPropertyManagementCreatesPropertyManagementWhenEmailDoesNotExist() {
        SignupRequest signupRequest = new SignupRequest("nonExistingEmail@test.com", "PropertyManagement",
                "propertymanagementpassword", "123456789");

        User mockUser = new User(1L, signupRequest.getName(), signupRequest.getEmail(),
                new BCryptPasswordEncoder().encode(signupRequest.getPassword()), UserRole.PROPERTY_MANAGEMENT,
                signupRequest.getPhoneNumber());

        when(userRepository.findFirstByEmail(signupRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserDto result = authService.createPropertyManagement(signupRequest);

        assertNotNull(result);
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertEquals(mockUser.getPassword(), result.getPassword());
        assertEquals(mockUser.getUserRole(), result.getUserRole());
        assertEquals(mockUser.getPhoneNumber(), result.getPhoneNumber());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createPropertyManagementThrowsExceptionWhenEmailExists() {
        SignupRequest signupRequest = new SignupRequest("existingEmail@test.com", "PropertyManagement",
                "propertymanagementpassword", "123456789");

        when(userRepository.findFirstByEmail(signupRequest.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> authService.createPropertyManagement(signupRequest));
    }

    /*@Test
    void hasUserWithEmailReturnsTrueWhenUserExists() {
        when(userRepository.findFirstByEmail("existingEmail@test.com")).thenReturn(Optional.of(new User()));
        assertTrue(authService.hasUserWithEmail("existingEmail@test.com"));
    }

    @Test
    void hasUserWithEmailReturnsFalseWhenUserDoesNotExist() {
        when(userRepository.findFirstByEmail("nonExistingEmail@test.com")).thenReturn(Optional.empty());
        assertFalse(authService.hasUserWithEmail("nonExistingEmail@test.com"));
    }*/
}