package FHCampus.MyFlat.services.customer;

import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.dtos.ApartmentDtoList;
import FHCampus.MyFlat.dtos.SearchApartmentDto;
import FHCampus.MyFlat.entities.Apartment;

import FHCampus.MyFlat.entities.BookApartment;
import FHCampus.MyFlat.repositories.ApartmentRepository;
import FHCampus.MyFlat.repositories.BookApartmentRepository;
import FHCampus.MyFlat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ApartmentRepository apartmentRepository;



    private final BookApartmentRepository bookApartmentRepository;



    @Override
    public List<ApartmentDto> getAllApartments() {
        return apartmentRepository.findAll().stream().map(Apartment::getApartmentDto).collect(Collectors.toList());
    }

    @Override
    public ApartmentDto getApartmentById(Long carId) {
        Optional<Apartment> optionalCar = apartmentRepository.findById(carId);
        return optionalCar.map(Apartment::getApartmentDto).orElse(null);
    }

    @Override
    public boolean bookApartment(Long carId, BookApartmentDto bookApartmentDto) {
        return false;
    }



    @Override
    public ApartmentDtoList searchApartment(SearchApartmentDto searchApartmentDto) {
        return null;
    }



    @Override
    public List<BookApartmentDto> getBookingsByUserId(Long userId) {
        return bookApartmentRepository.findAllByUserId(userId).stream().map(BookApartment::getBookApartmentDto).collect(Collectors.toList());
    }

}
