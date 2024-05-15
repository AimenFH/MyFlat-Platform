package FHCampus.MyFlat.services.admin;

import FHCampus.MyFlat.dtos.*;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postProperty(PropertyDto propertyDto);

    boolean postApartment(ApartmentDto apartmentDto);

    List<ApartmentDto> getAllApartments();

    void deleteApartment(Long carId);

    ApartmentDto getApartmentById(Long cardId);

    boolean updateApartment(Long carId, ApartmentDto apartmentDto) throws IOException;

    List<BookApartmentDto> getBookings();

    boolean changeBookingStatus(Long bookingId, String status);

    ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto);

}
