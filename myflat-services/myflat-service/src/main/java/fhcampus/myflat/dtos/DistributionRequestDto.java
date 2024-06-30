package fhcampus.myflat.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DistributionRequestDto {

    private String massage;
    private byte[]  document;
    private Integer buildingId;
    private Integer topId;
    private String title;
}
