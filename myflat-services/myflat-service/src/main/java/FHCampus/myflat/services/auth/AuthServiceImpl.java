package fhcampus.myflat.services.auth;

import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.enums.UserRole;
import fhcampus.myflat.exceptions.EmailAlreadyExistsException;
import fhcampus.myflat.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

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
