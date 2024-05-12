package FHCampus.CarRental.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CarDto {

    private Long id;

    private String name;

    private String color;

    private String transmission;

    private String brand;

    private String type;

    private String modelYear;

    private String description;

    private Integer price;

    private MultipartFile image;

    private byte[] returnedImage;

    public CarDto(String testCar, String ford, String blue, int i, String suv, String manual, String number, String description, byte[] bytes) {
    }

    public CarDto() {

    }
}
