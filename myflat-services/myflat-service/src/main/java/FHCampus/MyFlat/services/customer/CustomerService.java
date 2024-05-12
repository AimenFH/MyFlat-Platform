package FHCampus.MyFlat.services.customer;

import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.ApartmentDtoList;
import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.dtos.SearchApartmentDto;

import java.util.List;

public interface CustomerService {

    List<ApartmentDto> getAllCars();

    ApartmentDto getCarById(Long carId);

    boolean bookACar(Long carId, BookApartmentDto bookApartmentDto);

    List<BookApartmentDto> getBookingsByUserId(Long userId);

    ApartmentDtoList searchCar(SearchApartmentDto searchApartmentDto);

}
