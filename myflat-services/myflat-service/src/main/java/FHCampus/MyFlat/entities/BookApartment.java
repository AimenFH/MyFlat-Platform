package FHCampus.MyFlat.entities;

import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.enums.BookApartmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class BookApartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date toDate;

    @NotBlank
    @Size(max = 50)
    private Long months;

    @NotBlank
    @Size(max = 50)
    private Long amount;


     @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Property property;

    private BookApartmentStatus bookApartmentStatus;

    public BookApartmentDto getBookApartmentDto() {
        BookApartmentDto bookApartmentDto = new BookApartmentDto();
        bookApartmentDto.setId(id);
        bookApartmentDto.setFromDate(fromDate);
        bookApartmentDto.setToDate(toDate);
        bookApartmentDto.setMonths(months);
        bookApartmentDto.setAmount(amount);
        bookApartmentDto.setBookApartmentStatus(bookApartmentStatus);
        bookApartmentDto.setEmail(user.getEmail());
        bookApartmentDto.setUsername(user.getName());
        bookApartmentDto.setUserId(user.getId());
        return bookApartmentDto;
    }

}
