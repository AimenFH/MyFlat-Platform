package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.Document;
import fhcampus.myflat.entities.Notifications;
import fhcampus.myflat.repositories.DocumentRepository;
import fhcampus.myflat.repositories.NotificationsRepository;
import fhcampus.myflat.services.Appointment.AppointmentService;
import fhcampus.myflat.services.Feedback.FeedbackService;
import fhcampus.myflat.services.defect.DefectService;
import fhcampus.myflat.services.propertymanagement.PropertyManagementService;
import fhcampus.myflat.services.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for tenant-related operations.
 * This controller handles operations such as retrieving tenant information, managing bookings,
 * reporting defects, managing documents, and handling feedback and appointments.
 */
@RestController
@RequestMapping("/api/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;
    private final DefectService defectService;
    private final DocumentRepository documentRepository;
    private final NotificationsRepository notificationsRepository;
    private final PropertyManagementService propertyManagementService;
    private final AppointmentService appointmentService;
    private final FeedbackService feedbackService;

    /**
     * Retrieves tenant information by tenant ID.
     * @param userId The ID of the tenant.
     * @return ResponseEntity containing the tenant's information or a not found status.
     */
    @GetMapping("/v1/{userId}")
    public ResponseEntity<UserDto> getTenantById(@PathVariable long userId) {
        UserDto userDto = tenantService.getTenantById(userId);
        if (userDto != null) return ResponseEntity.ok(userDto);
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves bookings made by a tenant.
     * @param userId The ID of the tenant.
     * @return ResponseEntity containing a list of bookings.
     */
    @GetMapping("/v1/apartment/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(tenantService.getBookingsByUserId(userId));
    }

    /**
     * Reports a defect associated with a tenant's apartment.
     * @param defectDto DTO containing defect details.
     * @param image The image file of the defect.
     * @return ResponseEntity indicating the result of the defect report.
     * @throws IOException If an error occurs during file processing.
     */
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
     * Retrieves documents associated with a tenant and their apartment.
     * @param userId The ID of the tenant.
     * @param apartmentId The ID of the apartment.
     * @return ResponseEntity containing a list of documents or a not found status.
     */
    @GetMapping("/v1/document/{userId}/{apartmentId}")
    public ResponseEntity<Object> getDocumentsByUserAndApartment(@PathVariable Long userId, @PathVariable Long apartmentId) {
        List<Document> documents = documentRepository.findAll();
        List<Document> userApartmentDocuments = documents.stream()
                .filter(document -> document.getUser().getId().equals(userId) && document.getApartment().getId().equals(apartmentId))
                .collect(Collectors.toList());

        if (!userApartmentDocuments.isEmpty()) {
            return new ResponseEntity<>(userApartmentDocuments.stream().map(Document::documentDto).collect(Collectors.toList()), HttpStatus.OK);
        }
        return new ResponseEntity<>("No documents found for the given user and apartment", HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves non-archived documents for a tenant.
     * @param userId The ID of the tenant.
     * @param apartmentId The ID of the apartment.
     * @return ResponseEntity containing a list of non-archived documents.
     */
    @GetMapping("/v1/document/{userId}/{apartmentId}/tenant")
    public ResponseEntity<Object> getTenantDocuments(@PathVariable Long userId, @PathVariable Long apartmentId) {
        List<Document> documents = documentRepository.findAll();
        List<Document> userApartmentDocuments = documents.stream()
                .filter(document -> document.getUser().getId().equals(userId) && document.getApartment().getId().equals(apartmentId) && !document.isArchived())
                .toList();

        return new ResponseEntity<>(userApartmentDocuments.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    /**
     * Retrieves general documents for a tenant.
     * @param userId The ID of the tenant.
     * @param apartmentId The ID of the apartment.
     * @return ResponseEntity containing a list of general documents.
     */
    @GetMapping("/v1/document/{userId}/{apartmentId}/general")
    public ResponseEntity<Object> getGeneralDocuments(@PathVariable Long userId, @PathVariable Long apartmentId) {
        List<Document> documents = documentRepository.findAll();
        List<Document> userApartmentDocuments = documents.stream()
                .filter(document -> document.getUser().getId().equals(userId) && document.getApartment().getId().equals(apartmentId) && !document.isArchived())
                .toList();

        return new ResponseEntity<>(userApartmentDocuments.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    /**
     * Retrieves archived documents for a tenant.
     * @param userId The ID of the tenant.
     * @param apartmentId The ID of the apartment.
     * @return ResponseEntity containing a list of archived documents.
     */
    @GetMapping("/v1/document/{userId}/{apartmentId}/archived")
    public ResponseEntity<Object> getArchivedDocuments(@PathVariable Long userId, @PathVariable Long apartmentId) {
        List<Document> documents = documentRepository.findAll();
        List<Document> userApartmentDocuments = documents.stream()
                .filter(document -> document.getUser().getId().equals(userId) && document.getApartment().getId().equals(apartmentId) && document.isArchived())
                .toList();

        return new ResponseEntity<>(userApartmentDocuments.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    /**
     * Retrieves defects reported by a tenant.
     * @param userId The ID of the tenant.
     * @return ResponseEntity containing a list of defects.
     */
    @GetMapping("/defects/user/{userId}")
    public ResponseEntity<List<DefectDto>> getDefectsByUserId(@PathVariable Long userId) {
        List<DefectDto> defects = defectService.getDefectsByUserId(userId);
        return ResponseEntity.ok(defects);
    }

    /**
     * Distributes a notification to tenants.
     * @param distributionRequestDto DTO containing distribution details.
     * @return ResponseEntity indicating the result of the distribution operation.
     */
    //////////////////////////// distribute notification
    @PostMapping("/v1/distribute")
    public ResponseEntity<?> distributeNotification(@RequestBody DistributionRequestDto distributionRequestDto) {
        boolean success = propertyManagementService.distributeNotification(distributionRequestDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Retrieves notifications by top ID.
     * @param topId The top ID used to filter notifications.
     * @return ResponseEntity containing a list of notifications or a not found status.
     */
    @GetMapping("/v1/notification/top/{topId}")
    public ResponseEntity<List<Notifications>> getNotificationsByTopId(@PathVariable Integer topId) {
        List<Notifications> notifications = notificationsRepository.findByTopId(topId);
        if (notifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(notifications);
        }
    }

    /**
     * Retrieves all appointments for a tenant.
     * @return ResponseEntity containing a list of appointments or a message indicating no appointments.
     */
    @GetMapping("/appointment/all")
    public ResponseEntity<Object> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            return ResponseEntity.ok("There are no appointments.");
        } else {
            return ResponseEntity.ok(appointments);
        }
    }

    /**
     * Adds feedback from a tenant.
     * @param feedbackDto DTO containing feedback details.
     * @return ResponseEntity containing the created feedback.
     */
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackDto> addFeedback(@RequestBody FeedbackDto feedbackDto) {
        FeedbackDto createdFeedback = feedbackService.createFeedback(feedbackDto);
        return ResponseEntity.ok(createdFeedback);
    }
}