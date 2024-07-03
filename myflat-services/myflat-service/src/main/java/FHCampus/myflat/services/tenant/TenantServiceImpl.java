package fhcampus.myflat.services.tenant;

import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.*;
import fhcampus.myflat.enums.BookApartmentStatus;
import fhcampus.myflat.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for tenant-related operations.
 * Provides functionality for tenant management, including retrieving tenant details,
 * booking apartments, and managing apartment bookings.
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final BookApartmentRepository bookApartmentRepository;
    private final PropertyRepository propertyRepository;

    /**
     * Retrieves tenant details by user ID.
     *
     * @param userId The ID of the user to retrieve details for.
     * @return UserDto The DTO containing user details or null if not found.
     */
    @Override
    public UserDto getTenantById(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(User::getUserDto).orElse(null);
    }

    /**
     * Retrieves apartment details by apartment ID.
     *
     * @param apartmentId The ID of the apartment to retrieve details for.
     * @return ApartmentDto The DTO containing apartment details or null if not found.
     */
    @Override
    public ApartmentDto getApartmentById(Long apartmentId) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);
        return optionalApartment.map(Apartment::getApartmentDto).orElse(null);
    }

    /**
     * Attempts to book an apartment for a user.
     * Validates if the apartment is available for the requested dates and belongs to the specified property.
     *
     * @param apartmentId The ID of the apartment to book.
     * @param bookApartmentDto The DTO containing booking details.
     * @return BookingResult The result of the booking attempt, including success status and message.
     */
    public BookingResult bookApartment(Long apartmentId, BookApartmentDto bookApartmentDto) {
        Optional<User> optionalUser = userRepository.findById(bookApartmentDto.getUserId());
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);
        Optional<Property> optionalProperty = propertyRepository.findById(bookApartmentDto.getPropertyId());

        // Check if all entities exist and the apartment belongs to the property
        if (optionalApartment.isPresent() && optionalUser.isPresent() && optionalProperty.isPresent()
                && optionalApartment.get().getProperty().equals(optionalProperty.get())) {

            // Check if the apartment is already booked for the provided dates
            List<BookApartment> overlappingBookings = bookApartmentRepository
                    .findByApartmentAndToDateGreaterThanEqualAndFromDateLessThanEqual(optionalApartment.get(),
                            bookApartmentDto.getFromDate(), bookApartmentDto.getToDate());

            if (!overlappingBookings.isEmpty()) {
                // Apartment is already booked for the provided dates
                return new BookingResult(false, "Apartment is already booked for the provided dates.");
            }

            // Create a new booking
            BookApartment bookApartment = new BookApartment();
            bookApartment.setUser(optionalUser.get());
            bookApartment.setApartment(optionalApartment.get());
            bookApartment.setProperty(optionalProperty.get());
            bookApartment.setFromDate(bookApartmentDto.getFromDate());
            bookApartment.setToDate(bookApartmentDto.getToDate());
            bookApartment.setBookApartmentStatus(BookApartmentStatus.CURRENTENANT);
            bookApartment.setTop(bookApartmentDto.getTop());
            bookApartmentRepository.save(bookApartment);

            return new BookingResult(true, "Booking successful.");
        }

        return new BookingResult(false, "One or more required entities do not exist or the apartment does not belong to the property.");
    }

    /**
     * Retrieves all bookings made by a specific user.
     *
     * @param userId The ID of the user to retrieve bookings for.
     * @return List<BookApartmentDto> A list of DTOs containing booking details.
     */
    @Override
    public List<BookApartmentDto> getBookingsByUserId(Long userId) {
        return bookApartmentRepository.findAllByUserId(userId).stream().map(BookApartment::getBookApartmentDto).collect(Collectors.toList());
    }

    /**
     * Searches for apartments based on provided criteria.
     * This method is not implemented yet.
     *
     * @param searchApartmentDto The DTO containing search criteria.
     * @return ApartmentDtoList A list of DTOs containing apartment details that match the search criteria.
     */
    @Override
    public ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto) {
        return null;
    }
}