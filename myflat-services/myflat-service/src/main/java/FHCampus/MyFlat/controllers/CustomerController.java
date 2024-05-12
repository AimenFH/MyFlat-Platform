package FHCampus.MyFlat.controllers;

import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.SearchApartmentDto;
import FHCampus.MyFlat.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/v1/cars")
    public ResponseEntity<List<ApartmentDto>> getAllCars() {
        List<ApartmentDto> apartmentDtoList = customerService.getAllCars();
        return ResponseEntity.ok(apartmentDtoList);
    }

    @GetMapping("/v1/car/{carId}")
    public ResponseEntity<ApartmentDto> getCarById(@PathVariable Long carId) {
        ApartmentDto apartmentDto = customerService.getCarById(carId);
        if (apartmentDto != null) return ResponseEntity.ok(apartmentDto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/v1/car/book/{carId}")
    public ResponseEntity<?> bookACar(@PathVariable Long carId, @RequestBody BookApartmentDto bookApartmentDto) {
        boolean success = customerService.bookACar(carId, bookApartmentDto);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/v1/car/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }

    @PostMapping("/v1/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchApartmentDto searchApartmentDto) {
        return ResponseEntity.ok(customerService.searchCar(searchApartmentDto));
    }
}
