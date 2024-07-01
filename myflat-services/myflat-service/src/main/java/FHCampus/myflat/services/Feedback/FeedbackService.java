package fhcampus.myflat.services.Feedback;

import fhcampus.myflat.dtos.FeedbackDto;
import fhcampus.myflat.entities.Feedback;
import fhcampus.myflat.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setTenantId(feedbackDto.getTenantId());
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