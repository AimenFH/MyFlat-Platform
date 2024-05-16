package FHCampus.MyFlat.services.customer;

import FHCampus.MyFlat.dtos.*;
import FHCampus.MyFlat.entities.*;
import FHCampus.MyFlat.enums.BookApartmentStatus;
import FHCampus.MyFlat.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final BookApartmentRepository bookApartmentRepository;
    private final PropertyRepository propertyRepository;
    private final DefectRepository defectRepository;


    @Override
    public UserDto getTenantById(long userId) {
        Optional<Users> optionalUser = userRepository.findById(userId);
        return optionalUser.map(Users::getUserDto).orElse(null);
    }

    @Override
    public DefectReport defectReport(Long defectId, DefectDto defectDto) {
        Optional<Users> optionalUser = userRepository.findById(defectDto.getUserId());
        Optional<Defect> optionalDefect = defectRepository.findById(defectId);
        Optional<Apartment> optionalApartment = apartmentRepository.findById(defectDto.getApartmentId());

        // Check if all entities exist
        if (!optionalUser.isPresent() || !optionalApartment.isPresent()) {
            return new DefectReport(false, "User or apartment does not exist.");
        }

        // Create a new defect
        Defect newDefect = new Defect();
        newDefect.setUser(optionalUser.get());
        newDefect.setApartment(optionalApartment.get());
        newDefect.setDescription(defectDto.getDescription());
        newDefect.setTimestamp(defectDto.getTimestamp());
        newDefect.setStatus(defectDto.getStatus());
        defectRepository.save(newDefect);

        return new DefectReport(true, "Defect reported successfully.");
    }



    @Override
    public ApartmentDto getApartmentById(Long apartmentId) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);
        return optionalApartment.map(Apartment::getApartmentDto).orElse(null);
    }


    public BookingResult bookApartment(Long apartmentId, BookApartmentDto bookApartmentDto) {
        Optional<Users> optionalUser = userRepository.findById(bookApartmentDto.getUserId());
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
