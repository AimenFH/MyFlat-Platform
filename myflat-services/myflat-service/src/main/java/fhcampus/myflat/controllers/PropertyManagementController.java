package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.*;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.DocumentRepository;
import fhcampus.myflat.repositories.KeyManagementRepository;
import fhcampus.myflat.repositories.UserRepository;
import fhcampus.myflat.services.KeyManagementService;
import fhcampus.myflat.services.auth.AuthService;
import fhcampus.myflat.services.defect.DefectService;
import fhcampus.myflat.services.propertymanagement.PropertyManagementService;
import fhcampus.myflat.services.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property-management")
public class PropertyManagementController {
    @Autowired
    private PropertyManagementService propertyManagementService;
    @Autowired
    private AuthService authService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private DefectService defectService;
    @Autowired
    private KeyManagementService keyManagementService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KeyManagementRepository keyManagementRepository;

    // region Property Section
    @PostMapping("/v1/property")
    public ResponseEntity<?> postProperty(@RequestBody PropertyDto propertyDto) {
        boolean success = propertyManagementService.postProperty(propertyDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    ///////////////////////////////////////////// region Apartment Section
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

    //////////////// region Tenant Section
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

    //////////////////////////// distribute notification
    @PostMapping("/v1/distribute")
    public ResponseEntity<?> distributeNotification(@RequestBody DistributionRequestDto distributionRequestDto) {
        boolean success = propertyManagementService.distributeNotification(distributionRequestDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("v1/notifications")
    public ResponseEntity<?> getNotifications(@RequestParam(required = false) Integer buildingId, @RequestParam(required = false) Integer topId) {
        try {
            List<Notifications> notifications = propertyManagementService.getNotifications(buildingId, topId);
            return ResponseEntity.ok(notifications);
        } catch (NoNotificationsFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //////////////////////////// Key Management
    @PostMapping("/v1/key-management")
    public ResponseEntity<?> createKeyManagement(@RequestBody KeyManagementDto keyManagementDto) {
        boolean success = keyManagementService.createKeyManagement(keyManagementDto);
        if (success) return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/v1/key-management")
    public ResponseEntity<List<KeyManagementDto>> getAllKeyManagements() {
        List<KeyManagementDto> keyManagementDtos = keyManagementService.getAllKeyManagements();
        return ResponseEntity.ok(keyManagementDtos);
    }

    @GetMapping("/v1/key-management/user/{userId}")
    public ResponseEntity<?> getKeyManagementByUserId(@PathVariable Integer userId) {
        Optional<List<KeyManagementDto>> optionalKeyManagementDtos = keyManagementService.getKeyManagementByUserId(userId);
        if (optionalKeyManagementDtos.isPresent()) {
            return ResponseEntity.ok(optionalKeyManagementDtos.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/v1/key-management")
    public ResponseEntity<String> updateKeyManagementByUserId(@RequestBody KeyManagementDto keyManagementDto) {
        List<KeyManagement> keyManagements = keyManagementRepository.findAllByUserId(keyManagementDto.getUserId());
        if (keyManagements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        keyManagements.forEach(keyManagement -> {
            if (!keyManagement.getIssuanceDate().equals(keyManagementDto.getIssuanceDate())) {
                keyManagement.setIssuanceDate(keyManagementDto.getIssuanceDate());
            }
            if (!keyManagement.getRedemptionDate().equals(keyManagementDto.getRedemptionDate())) {
                keyManagement.setRedemptionDate(keyManagementDto.getRedemptionDate());
            }
            if (keyManagement.isReplacementRequested() != keyManagementDto.isReplacementRequested()) {
                keyManagement.setReplacementRequested(keyManagementDto.isReplacementRequested());
            }
            if (!keyManagement.getKeysNumber().equals(keyManagementDto.getKeysNumber())) {
                keyManagement.setKeysNumber(keyManagementDto.getKeysNumber());
            }
        });
        keyManagementRepository.saveAll(keyManagements);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/v1/key-management")
    public ResponseEntity<String> deleteKeyManagementByUserId(@RequestParam Integer userId) {
        List<KeyManagement> keyManagements = keyManagementRepository.findAllByUserId(userId);
        if (keyManagements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        keyManagementRepository.deleteAll(keyManagements);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /////////////////////Document
    @PostMapping(value = "/v1/document")
    public ResponseEntity<Object> createDocument(
            @RequestPart("documentDto") DocumentDto documentDto,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            Optional<Apartment> apartment = apartmentRepository.findById(documentDto.getApartmentId());
            Optional<User> user = userRepository.findById(documentDto.getUserId());

            if (apartment.isPresent() && user.isPresent()) {
                Document document = new Document();
                document.setApartment(apartment.get());
                document.setTitle(documentDto.getTitle());
                document.setContent(file.getBytes());
                document.setArchived(documentDto.isArchived());
                document.setUser(user.get());
                Document savedDocument = documentRepository.save(document);
                return new ResponseEntity<>(savedDocument.documentDto(), HttpStatus.CREATED);
            } else if (apartment.isEmpty()) {
                return new ResponseEntity<>("Apartment not found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Error occurred while decoding file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/v1/document/{apartmentId}/{documentId}")
    public ResponseEntity<Object> updateDocumentState(@PathVariable Long apartmentId, @PathVariable Long documentId, @RequestBody DocumentDto documentDto) {
        try {
            byte[] fileContent = Base64.getDecoder().decode(documentDto.getContent());
            List<Document> documents = documentRepository.findAll();
            Optional<Document> apartmentDocument = documents.stream()
                    .filter(document -> document.getApartment().getId().equals(apartmentId) && document.getId().equals(documentId))
                    .findFirst();

            if (apartmentDocument.isPresent()) {
                Document document = apartmentDocument.get();
                document.setArchived(documentDto.isArchived());
                document.setContent(fileContent);
                Document updatedDocument = documentRepository.save(document);
                return new ResponseEntity<>(updatedDocument.documentDto(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Document not found for the given apartment", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error occurred while decoding file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/v1/document")
    public ResponseEntity<Object> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return new ResponseEntity<>(documents.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    /////////////////////////////// defects
    @GetMapping(value = "/v1/defects")
    public List<DefectDto> getAllDefects() {
        return defectService.getAllDefects();
    }
    // endregion
}
