package FHCampus.CarRental.servicesTest;

import FHCampus.CarRental.dtos.BookACarDto;
import FHCampus.CarRental.dtos.CarDto;
import FHCampus.CarRental.dtos.CarDtoList;
import FHCampus.CarRental.dtos.SearchCarDto;
import FHCampus.CarRental.entities.BookACar;
import FHCampus.CarRental.entities.Car;
import FHCampus.CarRental.enums.BookCarStatus;
import FHCampus.CarRental.repositories.BookACarRepository;
import FHCampus.CarRental.repositories.CarRepository;
import FHCampus.CarRental.services.admin.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private BookACarRepository bookACarRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testPostCar_exception() {
        CarDto carDto = new CarDto("Test Car", "Ford", "Blue", 50000, "SUV", "Manual", "2022", "Description", null);
        when(carRepository.save(any(Car.class))).thenThrow(RuntimeException.class);

        boolean result = adminService.postCar(carDto);
        assertFalse(result);
    }

    @Test
    void testGetAllCars() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(new Car(), new Car()));
        List<CarDto> result = adminService.getAllCars();
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteCar() {
        doNothing().when(carRepository).deleteById(anyLong());
        adminService.deleteCar(1L);
        verify(carRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetCarById_found() {
        Car car = new Car();
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        CarDto result = adminService.getCarById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetCarById_notFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        CarDto result = adminService.getCarById(1L);
        assertNull(result);
    }


    @Test
    void testUpdateCar_notFound() throws IOException {
        CarDto carDto = new CarDto();

        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean result = adminService.updateCar(1L, carDto);
        assertFalse(result);
    }



    @Test
    void testChangeBookingStatus_approved() {
        BookACar bookACar = new BookACar();
        when(bookACarRepository.findById(anyLong())).thenReturn(Optional.of(bookACar));
        when(bookACarRepository.save(any(BookACar.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = adminService.changeBookingStatus(1L, "Approve");
        assertTrue(result);
        assertEquals(BookCarStatus.APPROVED, bookACar.getBookCarStatus());
    }

    @Test
    void testChangeBookingStatus_rejected() {
        BookACar bookACar = new BookACar();
        when(bookACarRepository.findById(anyLong())).thenReturn(Optional.of(bookACar));
        when(bookACarRepository.save(any(BookACar.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = adminService.changeBookingStatus(1L, "Reject");
        assertTrue(result);
        assertEquals(BookCarStatus.REJECTED, bookACar.getBookCarStatus());
    }

    @Test
    void testChangeBookingStatus_notFound() {
        when(bookACarRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean result = adminService.changeBookingStatus(1L, "Approve");
        assertFalse(result);
    }

    @Test
    void testSearchCar() {
        Car car = new Car();
        car.setName("Test Car");
        car.setBrand("Ford");
        car.setColor("Blue");
        car.setPrice(50000);
        car.setType("SUV");
        car.setDescription("Description");
        car.setModelYear("2022");
        car.setTransmission("Manual");

        when(carRepository.findAll(any(Example.class))).thenReturn(Arrays.asList(car));

        SearchCarDto searchCarDto = new SearchCarDto();
        searchCarDto.setBrand("Ford");
        searchCarDto.setType("SUV");
        searchCarDto.setTransmission("Manual");
        searchCarDto.setColor("Blue");

        CarDtoList result = adminService.searchCar(searchCarDto);
        assertNotNull(result);
        assertEquals(1, result.getCarDtoList().size());
    }
}


