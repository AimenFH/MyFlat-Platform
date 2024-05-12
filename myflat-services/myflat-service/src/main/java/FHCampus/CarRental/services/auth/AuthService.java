package FHCampus.CarRental.services.auth;

import FHCampus.CarRental.dtos.SignupRequest;
import FHCampus.CarRental.dtos.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);

}
