package FHCampus.MyFlat.dtos;

import FHCampus.MyFlat.enums.BookApartmentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookApartmentDto {

    private Long id;

    private Date fromDate;

    private Date toDate;

    private Long months;

    private Long amount;

    private BookApartmentStatus bookApartmentStatus;

    private Long userId;

    private String email;

    private String username;

}
