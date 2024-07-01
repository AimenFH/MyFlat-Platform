package fhcampus.myflat.services;

import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.BookApartmentRepository;
import fhcampus.myflat.repositories.PropertyRepository;
import fhcampus.myflat.repositories.UserRepository;
import fhcampus.myflat.services.tenant.TenantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TenantServiceImplTest {

    @InjectMocks
    TenantServiceImpl tenantService;

    @Mock
    ApartmentRepository apartmentRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookApartmentRepository bookApartmentRepository;

    @Mock
    PropertyRepository propertyRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTenantById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = tenantService.getTenantById(1L);

        assertEquals(1L, userDto.getId());
        verify(userRepository, times(1)).findById(1L);
    }
}