package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.AppointmentDto;
import fhcampus.myflat.dtos.DistributionRequestDto;
import fhcampus.myflat.dtos.DocumentDto;
import fhcampus.myflat.dtos.FeedbackDto;
import fhcampus.myflat.entities.Apartment;
import fhcampus.myflat.entities.Document;
import fhcampus.myflat.entities.Notifications;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.exceptions.NoNotificationsFoundException;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.DocumentRepository;
import fhcampus.myflat.repositories.UserRepository;
import fhcampus.myflat.services.Appointment.AppointmentService;
import fhcampus.myflat.services.Feedback.FeedbackService;
import fhcampus.myflat.services.propertymanagement.PropertyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling property management related communications.
 * This includes operations related to notifications, documents, appointments, and feedback.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property-management")
public class PropertyManagementCommunicationController {

    private final PropertyManagementService propertyManagementService;
    private final DocumentRepository documentRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final FeedbackService feedbackService;

    /**
     * Distributes a notification to users.
     * @param distributionRequestDto Request body containing distribution details.
     * @return ResponseEntity indicating the result of the operation.
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
     * Retrieves notifications based on the provided building and top ID.
     * @param buildingId Optional building ID to filter notifications.
     * @param topId Optional top ID to filter notifications.
     * @return ResponseEntity with the list of notifications or an error message.
     */
    @GetMapping("v1/notifications")
    public ResponseEntity<?> getNotifications(@RequestParam(required = false) Integer buildingId, @RequestParam(required = false) Integer topId) {
        try {
            List<Notifications> notifications = propertyManagementService.getNotifications(buildingId, topId);
            return ResponseEntity.ok(notifications);
        } catch (NoNotificationsFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Creates a new document associated with an apartment and user.
     * @param documentDto Document details excluding the file content.
     * @param file The document file to be stored.
     * @return ResponseEntity with the created document or an error message.
     */
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

    /**
     * Archives a specified document by its ID.
     * @param documentId The ID of the document to archive.
     * @return ResponseEntity with the updated document or an error message.
     */
    @PutMapping("/v1/document/{documentId}/archive")
    public ResponseEntity<Object> archiveDocument(@PathVariable Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        if (document.isPresent()) {
            Document doc = document.get();
            doc.setArchived(true);
            Document updatedDocument = documentRepository.save(doc);
            return new ResponseEntity<>(updatedDocument.documentDto(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates the state of a document, including its archived status and content.
     * @param apartmentId The ID of the apartment associated with the document.
     * @param documentId The ID of the document to update.
     * @param documentDto Document details including the new content to be updated.
     * @return ResponseEntity with the updated document or an error message.
     */
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

    /**
     * Retrieves all non-archived documents.
     * @return ResponseEntity with a list of non-archived documents.
     */
    @GetMapping("/v1/document")
    public ResponseEntity<Object> getAllDocuments() {
        List<Document> documents = documentRepository.findAll().stream().filter(document -> !document.isArchived()).toList();
        return new ResponseEntity<>(documents.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    /**
     * Retrieves all archived documents.
     * @return ResponseEntity with a list of archived documents.
     */
    @GetMapping("/v1/document/archived")
    public ResponseEntity<Object> getAllArchivedDocuments() {
        List<Document> documents = documentRepository.findAll().stream().filter(Document::isArchived).toList();
        return new ResponseEntity<>(documents.stream().map(Document::documentDto).toList(), HttpStatus.OK);
    }

    /**
     * Deletes all documents.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/v1/document")
    public ResponseEntity<Void> deleteAllDocuments() {
        documentRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a specific document by its ID.
     * @param documentId The ID of the document to delete.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/v1/document/{documentId}")
    public ResponseEntity<?> deleteDocumentById(@PathVariable Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        if (document.isPresent()) {
            documentRepository.deleteById(documentId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
        }
    }

    /**
     * Creates a new appointment.
     * @param appointmentDto Details of the appointment to be created.
     * @return ResponseEntity with a success or error message.
     */
    ////////////////////////// Appointment
    @PostMapping("/v1/appointment/create")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        boolean isCreated = appointmentService.createAppointment(appointmentDto);
        if (isCreated) {
            return ResponseEntity.ok("Appointment created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create appointment.");
        }
    }

    /**
     * Deletes an appointment by its ID.
     * @param id The ID of the appointment to delete.
     * @return ResponseEntity with a success or error message.
     */
    @DeleteMapping("/v1/appointment/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok("Appointment deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete appointment.");
        }
    }

    /**
     * Retrieves all appointments.
     * @return ResponseEntity with a list of appointments or a message indicating no appointments.
     */
    @GetMapping("/v1/appointment/all")
    public ResponseEntity<Object> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            return ResponseEntity.ok("There are no appointments.");
        } else {
            return ResponseEntity.ok(appointments);
        }
    }

    /**
     * Retrieves all feedback.
     * @return ResponseEntity with a list of all feedback.
     */
    ////////feedback
    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackDto>> getAllFeedback() {
        List<FeedbackDto> allFeedback = feedbackService.getAllFeedback();
        return ResponseEntity.ok(allFeedback);
    }
}