package FHCampus.CarRental.entities;

import FHCampus.CarRental.dtos.CarDto;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String color;

    @NotBlank
    @Size(max = 50)
    private String transmission;

    @NotBlank
    @Size(max = 50)
    private String brand;

    @NotBlank
    @Size(max = 50)
    private String type;

    @NotBlank
    @Size(max = 50)
    private String modelYear;

    @NotBlank
    @Size(max = 50)
    private String description;

    @NotBlank
    @Size(max = 50)
    private Integer price;

    @Column(columnDefinition = "longblob")
    private byte[] image;

    public CarDto getCarDto() {
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setName(name);
        carDto.setDescription(description);
        carDto.setColor(color);
        carDto.setType(type);
        carDto.setPrice(price);
        carDto.setTransmission(transmission);
        carDto.setModelYear(modelYear);
        carDto.setBrand(brand);
        carDto.setReturnedImage(image);
        return carDto;
    }


}
