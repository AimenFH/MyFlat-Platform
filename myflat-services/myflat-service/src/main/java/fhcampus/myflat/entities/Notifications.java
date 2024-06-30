package fhcampus.myflat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fhcampus.myflat.dtos.DistributionRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer buildingId;

    private Integer topId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String massage;

    @Lob
    private byte[] document;

    @JsonIgnore
    public DistributionRequestDto getDistributionRequestDto() {
        DistributionRequestDto distributionRequestDto = new DistributionRequestDto();
        distributionRequestDto.setBuildingId(buildingId);
        distributionRequestDto.setTopId(topId);
        distributionRequestDto.setTitle(title);
        distributionRequestDto.setMassage(massage);
        distributionRequestDto.setDocument(document);
        return distributionRequestDto;
    }
}
