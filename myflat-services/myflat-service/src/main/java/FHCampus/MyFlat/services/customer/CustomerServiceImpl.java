package FHCampus.MyFlat.services.customer;

import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.dtos.ApartmentDtoList;
import FHCampus.MyFlat.dtos.SearchApartmentDto;
import FHCampus.MyFlat.entities.Apartment;

import FHCampus.MyFlat.repositories.CarRepository;
import FHCampus.MyFlat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;



    @Override
    public List<ApartmentDto> getAllCars() {
        return carRepository.findAll().stream().map(Apartment::getApartmentDto).collect(Collectors.toList());
    }

    @Override
    public ApartmentDto getCarById(Long carId) {
        Optional<Apartment> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Apartment::getApartmentDto).orElse(null);
    }

    @Override
    public boolean bookACar(Long carId, BookApartmentDto bookApartmentDto) {
        return false;
    }

    @Override
    public List<BookApartmentDto> getBookingsByUserId(Long userId) {
        return null;
    }

    @Override
    public ApartmentDtoList searchCar(SearchApartmentDto searchApartmentDto) {
        return null;
    }

//    @Override
//    public boolean bookACar(Long carId, BookApartmentDto bookACarDto) {
//        Optional<Users> optionalUser = userRepository.findById(bookACarDto.getUserId());
//        Optional<Apartment> optionalCar = carRepository.findById(carId);
//        if (optionalCar.isPresent() && optionalUser.isPresent()) {
//            BookApartment bookApartment = new BookApartment();
//            long diffInMilliseconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
//            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliseconds);
//            bookApartment.setDays(days);
//            bookApartment.setUser(optionalUser.get());
//            bookApartment.setApartment(optionalCar.get());
//            bookApartment.setAmount(optionalCar.get().getPrice() * days);
//            bookApartment.setFromDate(bookACarDto.getFromDate());
//            bookApartment.setToDate(bookACarDto.getToDate());
//            bookApartment.setBookApartmentStatus(BookApartmentStatus.PENDING);
//            bookACarRepository.save(bookApartment);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public List<BookApartmentDto> getBookingsByUserId(Long userId) {
//        return bookACarRepository.findAllByUserId(userId).stream().map(BookApartment::getBookACarDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public ApartmentDtoList searchCar(SearchApartmentDto searchCarDto) {
//        Apartment apartment = new Apartment();
//        apartment.setBrand(searchCarDto.getBrand());
//        apartment.setType(searchCarDto.getType());
//        apartment.setTransmission(searchCarDto.getTransmission());
//        apartment.setColor(searchCarDto.getColor());
//        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
//                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
//                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
//                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
//                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
//        Example<Apartment> carExample = Example.of(apartment,exampleMatcher);
//        List<Apartment> apartments = carRepository.findAll(carExample);
//        ApartmentDtoList carDtoList = new ApartmentDtoList();
//        carDtoList.setApartmentDtoList(apartments.stream().map(Apartment::getCarDto).collect(Collectors.toList()));
//        return carDtoList;
//    }

}
