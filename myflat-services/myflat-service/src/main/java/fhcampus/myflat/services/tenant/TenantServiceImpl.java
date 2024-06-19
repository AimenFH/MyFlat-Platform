package fhcampus.myflat.services.tenant;

import fhcampus.myflat.enums.BookApartmentStatus;
import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.*;
import fhcampus.myflat.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final BookApartmentRepository bookApartmentRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public UserDto getTenantById(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(User::getUserDto).orElse(null);
    }

    @Override
    public ApartmentDto getApartmentById(Long apartmentId) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);
        return optionalApartment.map(Apartment::getApartmentDto).orElse(null);
    }


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
            bookApartmentRepository.save(bookApartment);

            return new BookingResult(true, "Booking successful.");
        }

        return new BookingResult(false, "One or more required entities do not exist or the apartment does not belong to the property.");
    }


    @Override
    public List<BookApartmentDto> getBookingsByUserId(Long userId) {
        return bookApartmentRepository.findAllByUserId(userId).stream().map(BookApartment::getBookApartmentDto).collect(Collectors.toList());
    }

    @Override
    public ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto) {
        return null;
    }
}
