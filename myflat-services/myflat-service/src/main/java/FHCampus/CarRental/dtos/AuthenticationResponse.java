package FHCampus.CarRental.dtos;


import FHCampus.CarRental.enums.UserRole;
import lombok.Data;


@Data
public class AuthenticationResponse {

    private String jwt;

    private UserRole userRole;

    private Long userId;

}
