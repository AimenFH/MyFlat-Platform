package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.Document;
import fhcampus.myflat.entities.Notifications;
import fhcampus.myflat.repositories.AppointmentRepository;
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

    @GetMapping("/v1/{userId}")
    public ResponseEntity<UserDto> getTenantById(@PathVariable long userId) {
        UserDto userDto = tenantService.getTenantById(userId);
        if (userDto != null) return ResponseEntity.ok(userDto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/v1/apartment/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(tenantService.getBookingsByUserId(userId));
    }

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

    @GetMapping("/v1/document/{userId}/{apartmentId}/tenant")
    public ResponseEntity<Object> getTenantDocuments(@PathVariable Long userId, @PathVariable Long apartmentId) {
        List<Document> documents = documentRepository.findAll();
        List<Document> userApartmentDocuments = documents.stream()
                .filter(document -> document.getUser().getId().equals(userId) && document.getApartment().getId().equals(apartmentId) && !document.isArchived())
                .collect(Collectors.toList());

        return new ResponseEntity<>(userApartmentDocuments.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/v1/document/{userId}/{apartmentId}/general")
    public ResponseEntity<Object> getGeneralDocuments(@PathVariable Long userId, @PathVariable Long apartmentId) {
        List<Document> documents = documentRepository.findAll();
        List<Document> userApartmentDocuments = documents.stream()
                .filter(document -> document.getUser().getId().equals(userId) && document.getApartment().getId().equals(apartmentId) && !document.isArchived())
                .collect(Collectors.toList());

        return new ResponseEntity<>(userApartmentDocuments.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/v1/document/{userId}/{apartmentId}/archived")
    public ResponseEntity<Object> getArchivedDocuments(@PathVariable Long userId, @PathVariable Long apartmentId) {
        List<Document> documents = documentRepository.findAll();
        List<Document> userApartmentDocuments = documents.stream()
                .filter(document -> document.getUser().getId().equals(userId) && document.getApartment().getId().equals(apartmentId) && document.isArchived())
                .toList();

        return new ResponseEntity<>(userApartmentDocuments.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/defects/user/{userId}")
    public ResponseEntity<List<DefectDto>> getDefectsByUserId(@PathVariable Long userId) {
        List<DefectDto> defects = defectService.getDefectsByUserId(userId);
        return ResponseEntity.ok(defects);
    }

    //////////////////////////// distribute notification
    @PostMapping("/v1/distribute")
    public ResponseEntity<?> distributeNotification(@RequestBody DistributionRequestDto distributionRequestDto) {
        boolean success = propertyManagementService.distributeNotification(distributionRequestDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/v1/notification/top/{topId}")
    public ResponseEntity<List<Notifications>> getNotificationsByTopId(@PathVariable Integer topId) {
        List<Notifications> notifications = notificationsRepository.findByTopId(topId);
        if (notifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(notifications);
        }
    }

    @GetMapping("/appointment/all")
    public ResponseEntity<Object> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            return ResponseEntity.ok("There are no appointments.");
        } else {
            return ResponseEntity.ok(appointments);
        }
    }

    @PostMapping("/feedback")
    public ResponseEntity<FeedbackDto> addFeedback(@RequestBody FeedbackDto feedbackDto) {
        FeedbackDto createdFeedback = feedbackService.createFeedback(feedbackDto);
        return ResponseEntity.ok(createdFeedback);
    }
}