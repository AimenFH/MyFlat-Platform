package FHCampus.MyFlat.services.customer;

import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.ApartmentDtoList;
import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.dtos.SearchApartmentDto;

import java.util.List;

public interface CustomerService {

    List<ApartmentDto> getAllApartments();

    ApartmentDto getApartmentById(Long carId);

    boolean bookApartment(Long carId, BookApartmentDto bookApartmentDto);

    List<BookApartmentDto> getBookingsByUserId(Long userId);

    ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto);

}
