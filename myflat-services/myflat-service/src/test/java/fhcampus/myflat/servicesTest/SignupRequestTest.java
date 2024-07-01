package fhcampus.myflat.servicesTest;

import fhcampus.myflat.dtos.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignupRequestTest {

    private SignupRequest signupRequest;

    @BeforeEach
    public void setUp() {
        signupRequest = new SignupRequest();
    }

    @Test
    public void testSetEmail() {
        String email = "test@example.com";
        signupRequest.setEmail(email);
        assertEquals(email, signupRequest.getEmail());
    }

    @Test
    public void testSetName() {
        String name = "Test User";
        signupRequest.setName(name);
        assertEquals(name, signupRequest.getName());
    }

    @Test
    public void testSetPassword() {
        String password = "password123";
        signupRequest.setPassword(password);
        assertEquals(password, signupRequest.getPassword());
    }

    @Test
    public void testSetPhoneNumber() {
        String phoneNumber = "1234567890";
        signupRequest.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, signupRequest.getPhoneNumber());
    }
}