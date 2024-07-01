package fhcampus.myflat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FeedbackDto {
    private Long tenantId;
    private String message;
    private LocalDateTime timestamp;

    public FeedbackDto() {

    }
}