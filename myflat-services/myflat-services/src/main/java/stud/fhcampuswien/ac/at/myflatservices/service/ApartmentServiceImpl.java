package stud.fhcampuswien.ac.at.myflatservices.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stud.fhcampuswien.ac.at.myflatservices.entity.Apartment;
import stud.fhcampuswien.ac.at.myflatservices.exception.EntityNotFoundException;
import stud.fhcampuswien.ac.at.myflatservices.repository.ApartmentRepository;
import stud.fhcampuswien.ac.at.myflatservices.service.base.ApartmentService;

import java.util.Optional;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    ApartmentRepository apartmentRepository;














    public static Apartment unwrapApartment(Optional<Apartment> entity, Long id) {
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Apartment.class);
    }
}
