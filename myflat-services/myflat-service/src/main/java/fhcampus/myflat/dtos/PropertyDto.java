package fhcampus.myflat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {

    private Long id;
    private String propertyName;
    private String propertyAddress;
    private Integer numberOfFloors;
    private Integer numberOfApartments;
}
