package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.FeedbackDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tenantId;
    private String message;
    private LocalDateTime timestamp;

    public FeedbackDto toDto() {
        FeedbackDto dto = new FeedbackDto();
        dto.setTenantId(this.tenantId);
        dto.setMessage(this.message);
        dto.setTimestamp(this.timestamp);
        return dto;
    }
}