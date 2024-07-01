package fhcampus.myflat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchApartmentDto {

    private int number;
    private int floor;
    private float area;
    private Integer price;
}
