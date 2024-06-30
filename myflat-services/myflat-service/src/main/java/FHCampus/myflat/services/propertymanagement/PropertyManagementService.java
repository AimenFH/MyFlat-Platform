package fhcampus.myflat.services.propertymanagement;

import fhcampus.myflat.dtos.*;
<<<<<<< HEAD:myflat-services/myflat-service/src/main/java/fhcampus/myflat/services/propertymanagement/PropertyManagementService.java
import fhcampus.myflat.entities.Notifications;
=======
import org.springframework.web.multipart.MultipartFile;
>>>>>>> 7eaa3ed5c07ba9ff5de62686302ab285743db8ab:myflat-services/myflat-service/src/main/java/FHCampus/myflat/services/propertymanagement/PropertyManagementService.java

import java.io.IOException;
import java.util.List;

public interface PropertyManagementService {

    boolean postProperty(PropertyDto propertyDto);

    boolean postApartment(ApartmentDto apartmentDto);

    List<ApartmentDto> getAllApartments();

    void deleteApartment(Long carId);

    ApartmentDto getApartmentById(Long cardId);

    boolean updateApartment(Long carId, ApartmentDto apartmentDto) throws IOException;

    List<BookApartmentDto> getBookings();

    boolean changeBookingStatus(Long bookingId, Integer status);

    ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto);

    boolean distributeNotification(DistributionRequestDto distributionRequestDto);

    List<Notifications> getNotifications(Integer buildingId, Integer topId);
}
