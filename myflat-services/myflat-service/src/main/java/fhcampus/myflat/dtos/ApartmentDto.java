package fhcampus.myflat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApartmentDto {

    private Long id;
    private Integer number;
    private int floor;
    private float area;
    private Integer price;
    private Long propertyId;
    public ApartmentDto() {
    }
}
