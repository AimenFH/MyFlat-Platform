package FHCampus.MyFlat.services.customer;

import FHCampus.MyFlat.dtos.*;

import java.util.List;

public interface CustomerService {


    ApartmentDto getApartmentById(Long carId);

    boolean bookApartment(Long carId, BookApartmentDto bookApartmentDto);

    List<BookApartmentDto> getBookingsByUserId(Long userId);

    ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto);

    UserDto getTenantById(long userId);
}
