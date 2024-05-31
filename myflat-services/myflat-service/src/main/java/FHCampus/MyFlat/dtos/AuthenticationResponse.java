package FHCampus.MyFlat.dtos;


import FHCampus.MyFlat.enums.UserRole;
import lombok.Data;


@Data
public class AuthenticationResponse {

    private String jwt;

    private UserRole userRole;

    private Long userId;
}
