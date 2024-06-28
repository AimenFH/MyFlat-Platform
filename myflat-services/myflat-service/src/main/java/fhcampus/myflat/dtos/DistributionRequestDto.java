package fhcampus.myflat.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DistributionRequestDto {

        private String message;
        private MultipartFile document;
        private Long buildingId;
        private Long topId;


}
