package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.KeyManagement;
import fhcampus.myflat.repositories.KeyManagementRepository;
import fhcampus.myflat.services.auth.AuthService;
import fhcampus.myflat.services.defect.DefectService;
import fhcampus.myflat.services.key.KeyManagementService;
import fhcampus.myflat.services.propertymanagement.PropertyManagementService;
import fhcampus.myflat.services.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller for managing property-related operations.
 * This includes handling properties, apartments, tenants, defects, and key management.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property-management")
public class PropertyManagementController {

    private final PropertyManagementService propertyManagementService;
    private final AuthService authService;
    private final TenantService tenantService;
    private final DefectService defectService;
    private final KeyManagementService keyManagementService;
    private final KeyManagementRepository keyManagementRepository;

    /**
     * Posts a new property.
     * @param propertyDto DTO containing property details.
     * @return ResponseEntity indicating the result of the operation.
     */
    ////////////////////////// region Property Section
    @PostMapping("/v1/property")
    public ResponseEntity<?> postProperty(@RequestBody PropertyDto propertyDto) {
        boolean success = propertyManagementService.postProperty(propertyDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Retrieves all properties.
     * @return ResponseEntity with a list of all properties.
     */
    @GetMapping("/v1/properties")
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        List<PropertyDto> propertyDtoList = propertyManagementService.getAllProperties();
        return ResponseEntity.ok(propertyDtoList);
    }

    /**
     * Posts a new apartment.
     * @param apartmentDto DTO containing apartment details.
     * @return ResponseEntity indicating the result of the operation.
     */
    ///////////////////////// region Apartment Section
    @PostMapping("/v1/apartment")
    public ResponseEntity<?> postApartment(@RequestBody ApartmentDto apartmentDto) {
        boolean success = propertyManagementService.postApartment(apartmentDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Retrieves all apartments.
     * @return ResponseEntity with a list of all apartments.
     */
    @GetMapping("/v1/apartments")
    public ResponseEntity<List<ApartmentDto>> getAllApartments() {
        List<ApartmentDto> apartmentDtoList = propertyManagementService.getAllApartments();
        return ResponseEntity.ok(apartmentDtoList);
    }

    /**
     * Deletes an apartment by its ID.
     * @param apartmentId ID of the apartment to delete.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long apartmentId) {
        propertyManagementService.deleteApartment(apartmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves an apartment by its ID.
     * @param apartmentId ID of the apartment.
     * @return ResponseEntity with the apartment details or not found status.
     */
    @GetMapping("/v1/apartment/{apartmentId}")
    public ResponseEntity<ApartmentDto> getApartmentById(@PathVariable Long apartmentId) {
        ApartmentDto apartmentDto = propertyManagementService.getApartmentById(apartmentId);
        if (apartmentDto != null) return ResponseEntity.ok(apartmentDto);
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates an apartment by its ID.
     * @param apartmentId ID of the apartment to update.
     * @param apartmentDto DTO containing new apartment details.
     * @return ResponseEntity indicating the result of the operation.
     * @throws IOException If an input or output exception occurred.
     */
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

    /**
     * Changes the booking status of an apartment.
     * @param bookingId ID of the booking to change.
     * @param status New status of the booking.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PutMapping("/v1/apartment/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable Integer status) {
        boolean success = propertyManagementService.changeBookingStatus(bookingId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves all booked apartments.
     * @return ResponseEntity with a list of all bookings.
     */
    @GetMapping("/v1/apartment/bookings")
    public ResponseEntity<?> getBookedApartment() {
        return ResponseEntity.ok(propertyManagementService.getBookings());
    }

    /**
     * Searches for apartments based on criteria.
     * @param searchApartmentDto DTO containing search criteria.
     * @return ResponseEntity with the search results.
     */
    @PostMapping("/v1/apartment/search")
    public ResponseEntity<?> searchApartment(@RequestBody SearchApartmentDto searchApartmentDto) {
        return ResponseEntity.ok(propertyManagementService.searchApartment(searchApartmentDto));
    }

    /**
     * Retrieves bookings by user ID.
     * @param userId ID of the user.
     * @return ResponseEntity with the bookings of the user.
     */
    //////////////// region Tenant Section
    @GetMapping("/v1/apartment/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(tenantService.getBookingsByUserId(userId));
    }

    /**
     * Retrieves a tenant by their ID.
     * @param userId ID of the tenant.
     * @return ResponseEntity with the tenant details or not found status.
     */
    @GetMapping("/v1/{userId}")
    public ResponseEntity<UserDto> getTenantById(@PathVariable long userId) {
        UserDto userDto = tenantService.getTenantById(userId);
        if (userDto != null) return ResponseEntity.ok(userDto);
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new tenant.
     * @param signupRequest DTO containing signup information.
     * @return ResponseEntity with the created tenant details or bad request status.
     */
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

    /**
     * Books an apartment for a user.
     * @param apartmentId ID of the apartment to book.
     * @param bookApartmentDto DTO containing booking details.
     * @return ResponseEntity indicating the result of the booking operation.
     */
    @PostMapping("/v1/apartment/book/{apartmentId}")
    public ResponseEntity<?> bookApartment(@PathVariable Long apartmentId, @RequestBody BookApartmentDto bookApartmentDto) {
        BookingResult bookingResult = tenantService.bookApartment(apartmentId, bookApartmentDto);
        if (bookingResult.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bookingResult.getMessage());
    }

    /**
     * Reports a defect.
     * @param defectDto DTO containing defect details.
     * @param image Image of the defect.
     * @return ResponseEntity indicating the result of the defect report operation.
     * @throws IOException If an input or output exception occurred.
     */
    ///////////////////// region Defect Section
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

    /**
     * Retrieves all defects.
     * @return List of all defects.
     */
    @GetMapping(value = "/v1/defects")
    public List<DefectDto> getAllDefects() {
        return defectService.getAllDefects();
    }

    /**
     * Creates a new key management entry.
     * @param keyManagementDto DTO containing key management details.
     * @return ResponseEntity indicating the result of the operation.
     */
    //////////////////////////// Key Management
    @PostMapping("/v1/key-management")
    public ResponseEntity<?> createKeyManagement(@RequestBody KeyManagementDto keyManagementDto) {
        boolean success = keyManagementService.createKeyManagement(keyManagementDto);
        if (success) return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Retrieves all key management entries.
     * @return ResponseEntity with a list of all key management entries.
     */
    @GetMapping("/v1/key-management")
    public ResponseEntity<List<KeyManagementDto>> getAllKeyManagements() {
        List<KeyManagementDto> keyManagementDtos = keyManagementService.getAllKeyManagements();
        return ResponseEntity.ok(keyManagementDtos);
    }

    /**
     * Retrieves key management entries by user ID.
     * @param userId ID of the user.
     * @return ResponseEntity with the key management entries of the user or not found status.
     */
    @GetMapping("/v1/key-management/user/{userId}")
    public ResponseEntity<?> getKeyManagementByUserId(@PathVariable Integer userId) {
        Optional<List<KeyManagementDto>> optionalKeyManagementDtos = keyManagementService.getKeyManagementByUserId(userId);
        if (optionalKeyManagementDtos.isPresent()) {
            return ResponseEntity.ok(optionalKeyManagementDtos.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    /**
     * Updates a key management entry by user ID.
     * @param keyManagementDto DTO containing new key management details.
     * @return ResponseEntity indicating the result of the update operation.
     */
    @PutMapping("/v1/key-management")
    public ResponseEntity<String> updateKeyManagementByUserId(@RequestBody KeyManagementDto keyManagementDto) {
        KeyManagement keyManagement = keyManagementRepository.findById(keyManagementDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
        keyManagement.update(keyManagementDto);
        keyManagementRepository.save(keyManagement);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Deletes a key management entry by its ID.
     * @param keyId The unique identifier of the key management entry to be deleted.
     * @return A ResponseEntity with HTTP status 204 (No Content) after successful deletion.
     */
    @DeleteMapping("/v1/key-management")
    public ResponseEntity<String> deleteKeyManagementByUserId(@RequestParam Long keyId) {
        KeyManagement keyManagement = keyManagementRepository.findById(keyId)
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
        keyManagementRepository.delete(keyManagement);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
