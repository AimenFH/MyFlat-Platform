package FHCampus.MyFlat.entities;

import FHCampus.MyFlat.dtos.ApartmentDto;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
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


    public ApartmentDto getApartmentDto() {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setId(id);
        apartmentDto.setNumber(number);
        apartmentDto.setFloor(floor);
        apartmentDto.setArea(area);
        apartmentDto.setPrice(price);
        return apartmentDto;
    }
}
