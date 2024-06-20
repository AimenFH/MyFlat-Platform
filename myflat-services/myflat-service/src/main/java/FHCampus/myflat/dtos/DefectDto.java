package fhcampus.myflat.dtos;


import fhcampus.myflat.entities.Defect;
import fhcampus.myflat.enums.DefectCategory;
import fhcampus.myflat.enums.DefectLocation;
import fhcampus.myflat.enums.DefectStatus;
import lombok.AllArgsConstructor;
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

    private DefectCategory category;

    private DefectLocation location;

    public DefectDto(Defect defect) {
        this.id = defect.getId();
        this.description = defect.getDescription();
        this.timestamp = defect.getTimestamp();
        this.userId = defect.getUser().getId();
        this.apartmentId = defect.getApartment().getId();
        this.status = defect.getStatus();
        this.category = defect.getCategory();
        this.location = defect.getLocation();
    }
}
