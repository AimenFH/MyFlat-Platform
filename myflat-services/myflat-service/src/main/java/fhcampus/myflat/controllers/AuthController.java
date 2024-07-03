package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.AuthenticationRequest;
import fhcampus.myflat.dtos.AuthenticationResponse;
import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.exceptions.EmailAlreadyExistsException;
import fhcampus.myflat.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests such as login and registration.
 * Utilizes AuthService to perform the actual authentication logic.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new property management user.
     *
     * @param signupRequest The signup request containing user details.
     * @return ResponseEntity with the created UserDto or an error message in case of failure.
     */
    @PostMapping("/v1/register/property-management")
    public ResponseEntity<?> registerPropertyManagement(@RequestBody SignupRequest signupRequest) {
        UserDto createdUserDto;
        try {
            createdUserDto = authService.createPropertyManagement(signupRequest);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    /**
     * Authenticates a user and generates a token.
     *
     * @param authenticationRequest The authentication request containing login credentials.
     * @return ResponseEntity with the AuthenticationResponse containing the token or an error message in case of failure.
     */
    @PostMapping("/v1/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse;
        try {
            authenticationResponse = authService.login(authenticationRequest);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }
}