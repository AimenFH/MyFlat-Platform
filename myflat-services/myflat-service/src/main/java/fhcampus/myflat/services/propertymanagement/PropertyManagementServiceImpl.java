package fhcampus.myflat.services.propertymanagement;

import fhcampus.myflat.entities.*;
import fhcampus.myflat.enums.BookApartmentStatus;
import fhcampus.myflat.repositories.*;
import fhcampus.myflat.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyManagementServiceImpl implements PropertyManagementService {

    private final ApartmentRepository apartmentRepository;
    private final PropertyRepository propertyRepository;
    private final BookApartmentRepository bookApartmentRepository;
    private UserRepository userRepository;


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


    @Override
    public List<ApartmentDto> getAllApartments() {
        return apartmentRepository.findAll().stream().map(Apartment::getApartmentDto).toList();
    }

    @Override
    public void deleteApartment(Long carId) {
        apartmentRepository.deleteById(carId);
    }

    @Override
    public ApartmentDto getApartmentById(Long cardId) {
        Optional<Apartment> optionalCar = apartmentRepository.findById(cardId);
        return optionalCar.map(Apartment::getApartmentDto).orElse(null);
    }

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

    @Override
    public List<BookApartmentDto> getBookings() {
        return bookApartmentRepository.findAll().stream().map(BookApartment::getBookApartmentDto).toList();
    }

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



    @Override
    public boolean distributeNotification(DistributionRequestDto distributionRequestDto) {
        try {
            Message message = new Message();
            message.setMessage(distributionRequestDto.getMessage());
            if (distributionRequestDto.getDocument() != null) {
                message.setDocument(distributionRequestDto.getDocument().getBytes());
            }

            List<BookApartment> users;
            if (distributionRequestDto.getBuildingId() == null && distributionRequestDto.getTopId() == null) {
                users = bookApartmentRepository.findAllUserIds();
            } else if (distributionRequestDto.getBuildingId() != null && distributionRequestDto.getTopId() == null) {
                users = bookApartmentRepository.findUserIdsByPropertyId(distributionRequestDto.getBuildingId());
            } else {
                users = bookApartmentRepository.findUserIdsByPropertyIdAndTop(distributionRequestDto.getBuildingId(), distributionRequestDto.getTopId());
            }

            for (BookApartment userApp : users) {
                Long userId = userApp.getUser().getId();
                Optional<User> userOptional = userRepository.findById(userId);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    user.getMessages().add(message);
                    userRepository.save(user);
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}