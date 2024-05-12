package FHCampus.MyFlat.services.auth;

import FHCampus.MyFlat.dtos.SignupRequest;
import FHCampus.MyFlat.dtos.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);

}
