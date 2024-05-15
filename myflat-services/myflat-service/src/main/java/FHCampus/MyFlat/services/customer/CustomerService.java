package FHCampus.MyFlat.services.customer;

import FHCampus.MyFlat.dtos.*;

import java.util.List;

public interface CustomerService {


    ApartmentDto getApartmentById(Long carId);

    BookingResult bookApartment(Long apartmentId, BookApartmentDto bookApartmentDto);

    List<BookApartmentDto> getBookingsByUserId(Long userId);

    ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto);

    UserDto getTenantById(long userId);
}
