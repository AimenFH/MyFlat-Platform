package fhcampus.myflat.servicesTest.tenant;

import fhcampus.myflat.dtos.*;

import java.util.List;

public interface TenantService {

    ApartmentDto getApartmentById(Long carId);

    BookingResult bookApartment(Long apartmentId, BookApartmentDto bookApartmentDto);

    List<BookApartmentDto> getBookingsByUserId(Long userId);

    ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto);

    UserDto getTenantById(long userId);
}
