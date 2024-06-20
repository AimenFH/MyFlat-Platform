package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.*;
import fhcampus.myflat.services.defect.DefectService;
import fhcampus.myflat.services.propertymanagement.PropertyManagementService;
import fhcampus.myflat.services.auth.AuthService;
import fhcampus.myflat.services.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property-management")
public class PropertyManagementController {

    private final PropertyManagementService propertyManagementService;
    private final AuthService authService;
    private final TenantService tenantService;
    private final DefectService defectService;

    // region Property Section
    @PostMapping("/v1/property")
    public ResponseEntity<?> postProperty(@RequestBody PropertyDto propertyDto) {
        boolean success = propertyManagementService.postProperty(propertyDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // endregion

    // region Apartment Section
    @PostMapping("/v1/apartment")
    public ResponseEntity<?> postApartment(@RequestBody ApartmentDto apartmentDto) {
        boolean success = propertyManagementService.postApartment(apartmentDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/v1/apartments")
    public ResponseEntity<List<ApartmentDto>> getAllApartments() {
        List<ApartmentDto> apartmentDtoList = propertyManagementService.getAllApartments();
        return ResponseEntity.ok(apartmentDtoList);
    }

    @DeleteMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long apartmentId) {
        propertyManagementService.deleteApartment(apartmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<ApartmentDto> getApartmentById(@PathVariable Long apartmentId) {
        ApartmentDto apartmentDto = propertyManagementService.getApartmentById(apartmentId);
        if (apartmentDto != null) return ResponseEntity.ok(apartmentDto);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<?> updateApartment(@PathVariable Long apartmentId, @ModelAttribute ApartmentDto apartmentDto) throws IOException {
        try {
            boolean success = propertyManagementService.updateApartment(apartmentId, apartmentDto);
            if (success) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PutMapping("/v1/apartment/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable Integer status) {
        boolean success = propertyManagementService.changeBookingStatus(bookingId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/v1/apartment/bookings")
    public ResponseEntity<?> getBookedApartment() {
        return ResponseEntity.ok(propertyManagementService.getBookings());
    }

    @PostMapping("/v1/apartment/search")
    public ResponseEntity<?> searchApartment(@RequestBody SearchApartmentDto searchApartmentDto) {
        return ResponseEntity.ok(propertyManagementService.searchApartment(searchApartmentDto));
    }
    // endregion

    // region Tenant Section
    @GetMapping("/v1/apartment/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(tenantService.getBookingsByUserId(userId));
    }

    @GetMapping("/v1/{userId}")
    public ResponseEntity<UserDto> getTenantById(@PathVariable long userId) {
        UserDto userDto = tenantService.getTenantById(userId);
        if (userDto != null) return ResponseEntity.ok(userDto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/v1/tenant/create")
    public ResponseEntity<?> createTenant(@RequestBody SignupRequest signupRequest) {
        UserDto createdUserDto;
        try {
            createdUserDto = authService.createTenant(signupRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/v1/apartment/book/{apartmentId}")
    public ResponseEntity<?> bookApartment(@PathVariable Long apartmentId, @RequestBody BookApartmentDto bookApartmentDto) {
        BookingResult bookingResult = tenantService.bookApartment(apartmentId, bookApartmentDto);
        if (bookingResult.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bookingResult.getMessage());
    }
    // endregion

    // region Defect Section
    @PostMapping(value = "/v1/defect")
    public ResponseEntity<String> reportDefect(
            @RequestPart("defectDto") DefectDto defectDto,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        try {
            defectService.reportDefect(defectDto, image);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Defect reported successfully.");
    }

    @GetMapping(value = "/v1/defects")
    public List<DefectDto> getAllDefects() {
        return defectService.getAllDefects();
    }
    // endregion
}
