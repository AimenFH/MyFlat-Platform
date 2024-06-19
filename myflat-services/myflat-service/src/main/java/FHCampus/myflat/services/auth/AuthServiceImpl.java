package fhcampus.myflat.services.auth;

import fhcampus.myflat.dtos.AuthenticationRequest;
import fhcampus.myflat.dtos.AuthenticationResponse;
import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.enums.UserRole;
import fhcampus.myflat.exceptions.EmailAlreadyExistsException;
import fhcampus.myflat.repositories.UserRepository;
import fhcampus.myflat.services.jwt.JwtUserService;
import fhcampus.myflat.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUserService jwtUserService;
    private final JwtUtil jwtUtil;

    /*@PostConstruct
    public void createPropertyManagementAccount(){
        Users propertyManagementAccount = userRepository.findByUserRole(UserRole.PROPERTY_MANAGEMENT);
        if (propertyManagementAccount == null){
            Users newPropertyManagementAccount = new Users();
            newPropertyManagementAccount.setName("Aimen");
            newPropertyManagementAccount.setUserRole(UserRole.PROPERTY_MANAGEMENT);
            newPropertyManagementAccount.setEmail("admin@test.com");
            newPropertyManagementAccount.setPhoneNumber("123456789");
            newPropertyManagementAccount.setPassword(new BCryptPasswordEncoder().encode("adminadmin"));
            userRepository.save(newPropertyManagementAccount);
        }
    }*/

    @Transactional
    @Override
    public UserDto createTenant(SignupRequest signupRequest) {
        return createUser(signupRequest, UserRole.TENANT);
    }

    @Transactional
    @Override
    public UserDto createPropertyManagement(SignupRequest signupRequest) {
        return createUser(signupRequest, UserRole.PROPERTY_MANAGEMENT);
    }

    @Transactional
    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        }
        final UserDetails userDetails = jwtUserService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }

    private UserDto createUser(SignupRequest signupRequest, UserRole userRole) {
        if (hasUserWithEmail(signupRequest.getEmail()))
            throw new EmailAlreadyExistsException("Email already exist. Try again with another email");

        User user = new User(signupRequest.getName(), signupRequest.getEmail(),
                new BCryptPasswordEncoder().encode(signupRequest.getPassword()), userRole, signupRequest.getPhoneNumber());
        User createdUser = userRepository.save(user);
        return new UserDto(createdUser);
    }

    private boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

}
