package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.ApartmentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "apartment")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "number cannot be blank")
    @Column(name = "number", nullable = false)
    private int number;

    @NotBlank(message = "floor cannot be blank")
    @Column(name = "floor", nullable = false)
    private int floor;

    @NotBlank(message = "floor cannot be blank")
    @Column(name = "area", nullable = false)
    private float area;

    @NotBlank
    @Size(max = 50)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Property property;

    public ApartmentDto getApartmentDto() {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setId(id);
        apartmentDto.setNumber(number);
        apartmentDto.setFloor(floor);
        apartmentDto.setArea(area);
        apartmentDto.setPrice(price);
        apartmentDto.setPropertyId(property.getId());
        return apartmentDto;
    }


}
