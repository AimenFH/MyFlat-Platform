package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.PropertyDto;
import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "propertyName cannot be blank")
    @NonNull
    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @NotBlank(message = "propertyAddress cannot be blank")
    @NonNull
    @Column(name = "property_address", nullable = false)
    private String propertyAddress;

    @NotBlank(message = "numberOfFloors cannot be blank")
    @Column(name = "number_of_floors", nullable = false)
    private Integer numberOfFloors;

    @NotBlank(message = "numberOfApartments cannot be blank")
    @Column(name = "number_of_apartments", nullable = false)
    private Integer numberOfApartments;


    public PropertyDto getPropertyDto() {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setId(id);
        propertyDto.setPropertyAddress(propertyAddress);
        propertyDto.setPropertyName(propertyName);
        propertyDto.setNumberOfFloors(numberOfFloors);
        propertyDto.setNumberOfApartments(numberOfApartments);
        return  propertyDto;
    }

}
