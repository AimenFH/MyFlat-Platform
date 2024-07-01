package fhcampus.myflat.dtos;


import fhcampus.myflat.enums.UserRole;
import lombok.Data;


@Data
public class AuthenticationResponse {

    private String jwt;
    private UserRole userRole;
    private Long userId;
}
