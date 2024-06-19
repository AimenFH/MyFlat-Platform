package fhcampus.myflat.dtos;

import fhcampus.myflat.enums.UserRole;
import fhcampus.myflat.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    private  String phoneNumber;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userRole = user.getUserRole();
        this.phoneNumber = user.getPhoneNumber();
    }

}
