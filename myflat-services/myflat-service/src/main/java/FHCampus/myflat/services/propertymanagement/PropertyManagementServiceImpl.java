package fhcampus.myflat.services.propertymanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.*;
import fhcampus.myflat.enums.BookApartmentStatus;
import fhcampus.myflat.exceptions.NoNotificationsFoundException;
import fhcampus.myflat.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service implementation for managing property-related operations.
 * This includes handling properties, apartments, bookings, and notifications.
 */
@Service
@RequiredArgsConstructor
public class PropertyManagementServiceImpl implements PropertyManagementService {

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private BookApartmentRepository bookApartmentRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Posts a new property to the repository.
     * @param propertyDto Data transfer object containing property details.
     * @return boolean indicating the success of the operation.
     */
    @Override
    public boolean postProperty(PropertyDto propertyDto) {
        try {
            Property property = new Property();
            property.setId(propertyDto.getId());
            property.setPropertyAddress(propertyDto.getPropertyAddress());
            property.setPropertyName(propertyDto.getPropertyName());
            property.setNumberOfApartments(propertyDto.getNumberOfApartments());
            property.setNumberOfFloors(propertyDto.getNumberOfFloors());
            propertyRepository.save(property);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Posts a new apartment to the repository.
     * @param apartmentDto Data transfer object containing apartment details.
     * @return boolean indicating the success of the operation.
     */
    @Override
    public boolean postApartment(ApartmentDto apartmentDto) {
        Optional<Property> optionalProperty = propertyRepository.findById(apartmentDto.getPropertyId());
        if (optionalProperty.isPresent()) {
            Apartment apartment = new Apartment();
            apartment.setId(apartmentDto.getId());
            apartment.setNumber(apartmentDto.getNumber());
            apartment.setFloor(apartmentDto.getFloor());
            apartment.setArea(apartmentDto.getArea());
            apartment.setPrice(apartmentDto.getPrice());
            apartment.setProperty(optionalProperty.get());
            apartmentRepository.save(apartment);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all apartments from the repository.
     * @return List of ApartmentDto representing all apartments.
     */
    @Override
    public List<ApartmentDto> getAllApartments() {
        return apartmentRepository.findAll().stream().map(Apartment::getApartmentDto).toList();
    }

    /**
     * Deletes an apartment by its ID.
     * @param carId The ID of the apartment to delete.
     */
    @Override
    public void deleteApartment(Long carId) {
        apartmentRepository.deleteById(carId);
    }

    /**
     * Retrieves an apartment by its ID.
     * @param cardId The ID of the apartment.
     * @return ApartmentDto of the requested apartment or null if not found.
     */
    @Override
    public ApartmentDto getApartmentById(Long cardId) {
        Optional<Apartment> optionalCar = apartmentRepository.findById(cardId);
        return optionalCar.map(Apartment::getApartmentDto).orElse(null);
    }

    /**
     * Updates an existing apartment with new details.
     * @param apartmentId The ID of the apartment to update.
     * @param apartmentDto Data transfer object containing new apartment details.
     * @return boolean indicating the success of the operation.
     */
    @Override
    public boolean updateApartment(Long apartmentId, ApartmentDto apartmentDto) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);
        if (optionalApartment.isPresent()) {
            Apartment existingApartment = optionalApartment.get();

            existingApartment.setNumber(apartmentDto.getNumber());
            existingApartment.setFloor(apartmentDto.getFloor());
            existingApartment.setArea(apartmentDto.getArea());
            existingApartment.setPrice(apartmentDto.getPrice());
            apartmentRepository.save(existingApartment);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all bookings from the repository.
     * @return List of BookApartmentDto representing all bookings.
     */
    @Override
    public List<BookApartmentDto> getBookings() {
        return bookApartmentRepository.findAll().stream().map(BookApartment::getBookApartmentDto).toList();
    }

    /**
     * Changes the booking status of an apartment booking.
     * @param bookingId The ID of the booking to change.
     * @param status The new status of the booking.
     * @return boolean indicating the success of the operation.
     */
    @Override
    public boolean changeBookingStatus(Long bookingId, Integer status) {
        Optional<BookApartment> optionalBookACar = bookApartmentRepository.findById(bookingId);
        if (optionalBookACar.isPresent()) {
            BookApartment existingBookApartment = optionalBookACar.get();
            if (Objects.equals(status, 0))
                existingBookApartment.setBookApartmentStatus(BookApartmentStatus.CURRENTENANT);
            else
                existingBookApartment.setBookApartmentStatus(BookApartmentStatus.FORMERTENANT);
            bookApartmentRepository.save(existingBookApartment);
            return true;
        }
        return false;
    }

    /**
     * Searches for apartments matching the given criteria.
     * @param searchApartmentDto Data transfer object containing search criteria.
     * @return ApartmentDtoList containing the search results.
     */
    @Override
    public ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto) {
        Apartment apartment = new Apartment();
        apartment.setNumber(searchApartmentDto.getNumber());
        apartment.setFloor(searchApartmentDto.getFloor());
        apartment.setArea(searchApartmentDto.getArea());
        apartment.setPrice(searchApartmentDto.getPrice());
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("number", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("floor", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("area", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("price", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Apartment> apartmentExampleExample = Example.of(apartment, exampleMatcher);
        List<Apartment> apartments = apartmentRepository.findAll(apartmentExampleExample);
        ApartmentDtoList carDtoList = new ApartmentDtoList();
        carDtoList.setApartmentDtoList(apartments.stream().map(Apartment::getApartmentDto).toList());
        return carDtoList;
    }

    /**
     * Distributes a notification to users.
     * @param distributionRequestDto Data transfer object containing distribution details.
     * @return boolean indicating the success of the operation.
     */
    @Override
    @JsonIgnore
    public boolean distributeNotification(DistributionRequestDto distributionRequestDto) {
        try {
            Notifications notifications = new Notifications();
            notifications.setBuildingId(distributionRequestDto.getBuildingId());
            notifications.setTopId(distributionRequestDto.getTopId());
            notifications.setTitle(distributionRequestDto.getTitle());
            notifications.setMassage(distributionRequestDto.getMassage());

            if (distributionRequestDto.getDocument() != null) {
                notifications.setDocument(distributionRequestDto.getDocument());
            }

            notificationsRepository.save(notifications);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieves notifications based on building and top ID.
     * @param buildingId Optional building ID to filter notifications.
     * @param topId Optional top ID to filter notifications.
     * @return List of Notifications matching the criteria.
     * @throws NoNotificationsFoundException if no notifications are found.
     */
    @Override
    public List<Notifications> getNotifications(Integer buildingId, Integer topId) throws NoNotificationsFoundException {
        List<Notifications> notifications;
        if (buildingId != null && topId != null) {
            notifications = notificationsRepository.findByBuildingIdAndTopId(buildingId, topId);
        } else if (buildingId != null) {
            notifications = notificationsRepository.findByBuildingId(buildingId);
        } else {
            notifications = notificationsRepository.findAll();
        }

        if (notifications.isEmpty()) {
            throw new NoNotificationsFoundException("No notifications found for the given criteria");
        }

        return notifications;
    }

    /**
     * Retrieves notifications for a specific user based on their apartment bookings.
     * @param userId The ID of the user.
     * @return List of Notifications relevant to the user.
     */
    @Override
    public List<Notifications> getNotificationsForUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        List<BookApartment> bookApartments = bookApartmentRepository.findByUserId(userId);

        List<Long> apartmentIds = bookApartments.stream()
                .map(bookApartment -> bookApartment.getApartment().getId())
                .toList();

        List<Notifications> notifications = new ArrayList<>();
        for (Long apartmentId : apartmentIds) {
            notifications.addAll(notificationsRepository.findByBuildingId(Math.toIntExact(apartmentId)));
        }

        return notifications;
    }

    /**
     * Retrieves all properties from the repository.
     * @return List of PropertyDto representing all properties.
     */
    @Override
    public List<PropertyDto> getAllProperties() {
        return propertyRepository.findAll().stream().map(Property::getPropertyDto).toList();
    }
}