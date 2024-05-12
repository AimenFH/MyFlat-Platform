package FHCampus.MyFlat.dtos;

import FHCampus.MyFlat.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    private  String phoneNumber;

}
