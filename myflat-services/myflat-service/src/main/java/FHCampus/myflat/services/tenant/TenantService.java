package fhcampus.myflat.services.tenant;

import fhcampus.myflat.dtos.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TenantService {

    ApartmentDto getApartmentById(Long carId);

    BookingResult bookApartment(Long apartmentId, BookApartmentDto bookApartmentDto);

    List<BookApartmentDto> getBookingsByUserId(Long userId);

    ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto);

    UserDto getTenantById(long userId);

    void reportDefect(DefectDto defectDto, MultipartFile image) throws IOException;
}
