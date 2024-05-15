package FHCampus.MyFlat.dtos;

import FHCampus.MyFlat.enums.BookApartmentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookApartmentDto {

    private Long id;

    private Date fromDate;

    private Date toDate;

    private Long amount;

    private Long userId;

    private Long propertyId;
}
