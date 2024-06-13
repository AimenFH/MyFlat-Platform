package fhcampus.myflat.services.auth;

import fhcampus.myflat.dtos.SignupRequest;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.entities.Users;
import fhcampus.myflat.enums.UserRole;
import fhcampus.myflat.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
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
    }

    @Override
    public UserDto createTenant(SignupRequest signupRequest) {
        Users user = new Users();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setUserRole(UserRole.TENANT);
        Users createdTenant = userRepository.save(user);
        UserDto cretaedUserDto = new UserDto();
        cretaedUserDto.setId(createdTenant.getId());
        cretaedUserDto.setName(createdTenant.getName());
        cretaedUserDto.setEmail(createdTenant.getEmail());
        cretaedUserDto.setPhoneNumber(createdTenant.getPhoneNumber());
        cretaedUserDto.setUserRole(createdTenant.getUserRole());
        return cretaedUserDto;
    }

    @Override
    public boolean hasTenantWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

}
