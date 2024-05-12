package FHCampus.MyFlat.services.admin;

import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.ApartmentDtoList;
import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.dtos.SearchApartmentDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(ApartmentDto apartmentDto);

    List<ApartmentDto> getAllCars();

    void deleteCar(Long carId);

    ApartmentDto getCarById(Long cardId);

    boolean updateCar(Long carId, ApartmentDto apartmentDto) throws IOException;

    List<BookApartmentDto> getBookings();

    boolean changeBookingStatus(Long bookingId, String status);

    ApartmentDtoList searchCar(SearchApartmentDto searchApartmentDto);

}
