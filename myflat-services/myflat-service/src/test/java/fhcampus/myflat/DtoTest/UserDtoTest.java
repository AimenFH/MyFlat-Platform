package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    @Test
    public void testGettersAndSetters() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password123");
        userDto.setUserRole(UserRole.TENANT);
        userDto.setPhoneNumber("1234567890");

        assertEquals(1L, userDto.getId());
        assertEquals("Test User", userDto.getName());
        assertEquals("test@example.com", userDto.getEmail());
        assertEquals("password123", userDto.getPassword());
        assertEquals(UserRole.TENANT, userDto.getUserRole());
        assertEquals("1234567890", userDto.getPhoneNumber());
    }
}