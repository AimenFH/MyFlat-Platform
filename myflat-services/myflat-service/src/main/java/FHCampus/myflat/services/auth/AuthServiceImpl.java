package fhcampus.myflat.services.auth;

import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.entities.Users;
import fhcampus.myflat.enums.UserRole;
import fhcampus.myflat.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
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
    public UserDto createPropertyManager(SignupRequest signupRequest) {
        return createUser(signupRequest, UserRole.PROPERTY_MANAGEMENT);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    private UserDto createUser(SignupRequest signupRequest, UserRole userRole) {
        Users user = new Users();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setUserRole(userRole);
        Users createdUser = userRepository.save(user);
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(createdUser.getId());
        createdUserDto.setName(createdUser.getName());
        createdUserDto.setEmail(createdUser.getEmail());
        createdUserDto.setPhoneNumber(createdUser.getPhoneNumber());
        createdUserDto.setUserRole(createdUser.getUserRole());
        return createdUserDto;
    }

}
