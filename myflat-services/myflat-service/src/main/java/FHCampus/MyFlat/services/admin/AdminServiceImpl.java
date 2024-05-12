package FHCampus.MyFlat.services.admin;

import FHCampus.MyFlat.dtos.ApartmentDto;
import FHCampus.MyFlat.dtos.ApartmentDtoList;
import FHCampus.MyFlat.dtos.BookApartmentDto;
import FHCampus.MyFlat.dtos.SearchApartmentDto;
import FHCampus.MyFlat.entities.Apartment;
import FHCampus.MyFlat.repositories.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ApartmentRepository apartmentRepository;



//    @Override
//    public boolean postCar(ApartmentDto carDto) {
//        try {
//            Apartment apartment = new Apartment();
//            apartment.setName(carDto.getName());
//            apartment.setBrand(carDto.getBrand());
//            apartment.setColor(carDto.getColor());
//            apartment.setPrice(carDto.getPrice());
//            apartment.setType(carDto.getType());
//            apartment.setDescription(carDto.getDescription());
//            apartment.setModelYear(carDto.getModelYear());
//            apartment.setTransmission(carDto.getTransmission());
//            apartment.setImage(carDto.getImage().getBytes());
//            carRepository.save(apartment);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    @Override
    public boolean postCar(ApartmentDto apartmentDto) {
        return false;
    }

    @Override
    public List<ApartmentDto> getAllCars() {
        return apartmentRepository.findAll().stream().map(Apartment::getApartmentDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long carId) {
        apartmentRepository.deleteById(carId);
    }

    @Override
    public ApartmentDto getCarById(Long cardId) {
        Optional<Apartment> optionalCar = apartmentRepository.findById(cardId);
        return optionalCar.map(Apartment::getApartmentDto).orElse(null);
    }

    @Override
    public boolean updateCar(Long carId, ApartmentDto apartmentDto) throws IOException {
        return false;
    }

    @Override
    public List<BookApartmentDto> getBookings() {
        return null;
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        return false;
    }

    @Override
    public ApartmentDtoList searchCar(SearchApartmentDto searchApartmentDto) {
        return null;
    }

//    @Override
//    public boolean updateCar(Long carId, ApartmentDto carDto) throws IOException {
//        Optional<Apartment> optionalCar = carRepository.findById(carId);
//        if (optionalCar.isPresent()) {
//            Apartment existingApartment = optionalCar.get();
//            existingApartment.setName(carDto.getName());
//            existingApartment.setBrand(carDto.getBrand());
//            existingApartment.setColor(carDto.getColor());
//            existingApartment.setPrice(carDto.getPrice());
//            existingApartment.setType(carDto.getType());
//            existingApartment.setDescription(carDto.getDescription());
//            existingApartment.setModelYear(carDto.getModelYear());
//            existingApartment.setTransmission(carDto.getTransmission());
//            if (carDto.getImage() != null)
//                existingApartment.setImage(carDto.getImage().getBytes());
//            carRepository.save(existingApartment);
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public List<BookApartmentDto> getBookings() {
//        return bookACarRepository.findAll().stream().map(BookApartment::getBookACarDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean changeBookingStatus(Long bookingId, String status) {
//        Optional<BookApartment> optionalBookACar = bookACarRepository.findById(bookingId);
//        if (optionalBookACar.isPresent()) {
//            BookApartment existingBookApartment = optionalBookACar.get();
//            if (Objects.equals(status, "Approve"))
//                existingBookApartment.setBookApartmentStatus(BookApartmentStatus.APPROVED);
//            else
//                existingBookApartment.setBookApartmentStatus(BookApartmentStatus.REJECTED);
//            bookACarRepository.save(existingBookApartment);
//            return true;
//        }
//        return false;
//    }

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
