package fhcampus.myflat.services.Feedback;

import fhcampus.myflat.dtos.FeedbackDto;
import fhcampus.myflat.entities.Feedback;
import fhcampus.myflat.repositories.FeedbackRepository;
import fhcampus.myflat.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;

    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setTenantId(userService.getCurrentUser().getId());
        feedback.setMessage(feedbackDto.getMessage());
        feedback.setTimestamp(feedbackDto.getTimestamp());
        feedback = feedbackRepository.save(feedback);
        return feedback.toDto();
    }

    public List<FeedbackDto> getAllFeedback() {
        return feedbackRepository.findAll().stream()
                .map(Feedback::toDto)
                .collect(Collectors.toList());
    }
}