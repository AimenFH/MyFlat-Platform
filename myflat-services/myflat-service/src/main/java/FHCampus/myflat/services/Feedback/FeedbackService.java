package fhcampus.myflat.services.Feedback;

import fhcampus.myflat.dtos.FeedbackDto;
import fhcampus.myflat.entities.Feedback;
import fhcampus.myflat.repositories.FeedbackRepository;
import fhcampus.myflat.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling feedback-related operations.
 * This class provides methods to create new feedback and retrieve all feedback from the repository.
 */
@RequiredArgsConstructor
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;

    /**
     * Creates a new feedback entry in the repository based on the provided FeedbackDto.
     * The current user's ID is automatically set as the tenantId for the feedback.
     * @param feedbackDto The feedback data transfer object containing the feedback details.
     * @return FeedbackDto The saved feedback data transferred back as DTO.
     */
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setTenantId(userService.getCurrentUser().getId());
        feedback.setMessage(feedbackDto.getMessage());
        feedback.setTimestamp(feedbackDto.getTimestamp());
        feedback = feedbackRepository.save(feedback);
        return feedback.toDto();
    }

    /**
     * Retrieves all feedback entries from the repository and converts them to DTOs.
     * @return List<FeedbackDto> A list of feedback DTOs.
     */
    public List<FeedbackDto> getAllFeedback() {
        return feedbackRepository.findAll().stream()
                .map(Feedback::toDto)
                .toList();
    }
}