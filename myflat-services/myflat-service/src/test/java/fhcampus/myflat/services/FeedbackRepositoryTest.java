package fhcampus.myflat.services;

import fhcampus.myflat.entities.Feedback;
import fhcampus.myflat.repositories.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
public class FeedbackRepositoryTest {

    @InjectMocks
    FeedbackRepository feedbackRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Feedback feedback1 = new Feedback();
        feedback1.setMessage("Feedback 1");
        Feedback feedback2 = new Feedback();
        feedback2.setMessage("Feedback 2");

        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback1, feedback2));

        List<Feedback> feedbacks = feedbackRepository.findAll();

        assertEquals(2, feedbacks.size());
        verify(feedbackRepository, times(1)).findAll();
    }
}