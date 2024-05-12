package FHCampus.MyFlat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApartmentDto {

    private Long id;

    private int number;

    private int floor;

    private float area;

    private Integer price;



    public ApartmentDto() {
    }
}
