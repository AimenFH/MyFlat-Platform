package FHCampus.CarRental.servicesTest;

import FHCampus.CarRental.dtos.BookACarDto;
import FHCampus.CarRental.dtos.CarDto;
import FHCampus.CarRental.dtos.SearchCarDto;
import FHCampus.CarRental.entities.BookACar;
import FHCampus.CarRental.entities.Car;
import FHCampus.CarRental.entities.Users;
import FHCampus.CarRental.enums.BookCarStatus;
import FHCampus.CarRental.repositories.BookACarRepository;
import FHCampus.CarRental.repositories.CarRepository;
import FHCampus.CarRental.repositories.UserRepository;
import FHCampus.CarRental.services.customer.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookACarRepository bookACarRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCars() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(new Car(), new Car()));
        assertEquals(2, customerService.getAllCars().size());
    }

    @Test
    void testGetCarById() {
        Optional<Car> car = Optional.of(new Car());
        when(carRepository.findById(anyLong())).thenReturn(car);
        assertNotNull(customerService.getCarById(1L));
    }

    @Test
    void testGetCarById_notFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(customerService.getCarById(1L));
    }


    @Test
    void testBookACar_notFound() {
        BookACarDto bookACarDto = new BookACarDto();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(customerService.bookACar(1L, bookACarDto));
    }


}
