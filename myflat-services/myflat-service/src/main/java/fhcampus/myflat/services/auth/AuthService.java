package fhcampus.myflat.services.auth;

import fhcampus.myflat.dtos.AuthenticationRequest;
import fhcampus.myflat.dtos.AuthenticationResponse;
import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;

public interface AuthService {

    UserDto createTenant(SignupRequest signupRequest);

    UserDto createPropertyManagement(SignupRequest signupRequest);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

}
