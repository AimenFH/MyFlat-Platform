package FHCampus.MyFlat.controllers;

import FHCampus.MyFlat.dtos.*;
import FHCampus.MyFlat.services.admin.AdminService;
import FHCampus.MyFlat.services.auth.AuthService;
import FHCampus.MyFlat.services.customer.CustomerService;
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
    private final AuthService authService;

    //////////////////////////////// Admin Property Section
    @PostMapping("/v1/property")
    public ResponseEntity<?> postProperty(@RequestBody PropertyDto propertyDto) {
        boolean success = adminService.postProperty(propertyDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    ////////////////////////////////  Admin Apartment Section
    @PostMapping("/v1/apartment")
    public ResponseEntity<?> postApartment(@RequestBody ApartmentDto apartmentDto) {
        boolean success = adminService.postApartment(apartmentDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/v1/apartments")
    public ResponseEntity<List<ApartmentDto>> getAllApartments() {
        List<ApartmentDto> apartmentDtoList = adminService.getAllApartments();
        return ResponseEntity.ok(apartmentDtoList);
    }

    @DeleteMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long apartmentId) {
        adminService.deleteApartment(apartmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<ApartmentDto> getApartmentById(@PathVariable Long apartmentId) {
        ApartmentDto apartmentDto = adminService.getApartmentById(apartmentId);
        if (apartmentDto != null) return ResponseEntity.ok(apartmentDto);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<?> updateApartment(@PathVariable Long apartmentId, @ModelAttribute ApartmentDto apartmentDto) throws IOException {
        try {
            boolean success = adminService.updateApartment(apartmentId, apartmentDto);
            if (success) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PutMapping("/v1/apartment/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable Integer status) {
        boolean success = adminService.changeBookingStatus(bookingId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/v1/apartment/bookings")
    public ResponseEntity<?> getBookedApartment() {
        return ResponseEntity.ok(adminService.getBookings());
    }

    @PostMapping("/v1/apartment/search")
    public ResponseEntity<?> searchApartment(@RequestBody SearchApartmentDto searchApartmentDto) {
        return ResponseEntity.ok(adminService.searchApartment(searchApartmentDto));
    }

    /////////////////////////////Admin customer service
    private final CustomerService customerService;

    @GetMapping("/v1/apartment/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }

    @GetMapping("/v1/{userId}")
    public ResponseEntity<UserDto> getTenantById(@PathVariable long userId) {
        UserDto userDto = customerService.getTenantById(userId);
        if (userDto != null) return ResponseEntity.ok(userDto);
        return ResponseEntity.notFound().build();
    }

    //////// sign up
    @PostMapping("/v1/signup")
    public ResponseEntity<?> createCustomer(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already exist. Try again with another email");
        UserDto createdUserDto = authService.createCustomer(signupRequest);
        if (createdUserDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request!");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/v1/apartment/book/{apartmentId}")
    public ResponseEntity<?> bookApartment(@PathVariable Long apartmentId, @RequestBody BookApartmentDto bookApartmentDto) {
        BookingResult bookingResult = customerService.bookApartment(apartmentId, bookApartmentDto);
        if (bookingResult.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bookingResult.getMessage());
    }
}
