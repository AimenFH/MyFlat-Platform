package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.AuthenticationRequest;
import fhcampus.myflat.dtos.AuthenticationResponse;
import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.exceptions.EmailAlreadyExistsException;
import fhcampus.myflat.servicesTest.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

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
