package fhcampus.myflat.entities;

import fhcampus.myflat.enums.BookApartmentStatus;
import fhcampus.myflat.dtos.BookApartmentDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private Integer amount;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apartment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Apartment apartment;

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
        bookApartmentDto.setAmount(amount);
        bookApartmentDto.setUserId(user.getId());
        bookApartmentDto.setPropertyId(apartment.getId());
        bookApartmentDto.setBookApartmentStatus(bookApartmentStatus);
        return bookApartmentDto;
    }
}
