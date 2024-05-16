package FHCampus.MyFlat.dtos;


import FHCampus.MyFlat.enums.DefectStatus;
import lombok.Data;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class DefectDto {

    private Long id;


    private String description;

    @Past(message = "Timestamp cannot be in the future")
    private Date timestamp;

    private Long userId;

    private Long apartmentId;

    private DefectStatus status;
}
