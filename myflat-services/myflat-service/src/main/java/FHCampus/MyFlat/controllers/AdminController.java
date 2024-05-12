package FHCampus.MyFlat.controllers;

import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.SearchApartmentDto;
import FHCampus.MyFlat.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/v1/car")
    public ResponseEntity<?> postCar(@ModelAttribute ApartmentDto apartmentDto) {
        boolean success = adminService.postCar(apartmentDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/v1/cars")
    public ResponseEntity<List<ApartmentDto>> getAllCars() {
        List<ApartmentDto> apartmentDtoList = adminService.getAllApartments();
        return ResponseEntity.ok(apartmentDtoList);
    }

    @DeleteMapping("/v1/car/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        adminService.deleteApartment(carId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/car/{carId}")
    public ResponseEntity<ApartmentDto> getCarById(@PathVariable Long carId) {
        ApartmentDto apartmentDto = adminService.getApartmentById(carId);
        if (apartmentDto != null) return ResponseEntity.ok(apartmentDto);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/v1/car/{carId}")
    public ResponseEntity<?> updateCar(@PathVariable Long carId, @ModelAttribute ApartmentDto apartmentDto) throws IOException {
        try {
            boolean success = adminService.updateApartment(carId, apartmentDto);
            if (success) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @GetMapping("/v1/car/bookings")
    public ResponseEntity<?> getBookings() {
        return ResponseEntity.ok(adminService.getBookings());
    }

    @GetMapping("/v1/car/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String status) {
        boolean success = adminService.changeBookingStatus(bookingId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/v1/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchApartmentDto searchApartmentDto) {
        return ResponseEntity.ok(adminService.searchApartment(searchApartmentDto));
    }
}
