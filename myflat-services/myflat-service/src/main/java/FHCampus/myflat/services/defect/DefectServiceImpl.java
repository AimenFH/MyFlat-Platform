package fhcampus.myflat.services.defect;

import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.entities.Apartment;
import fhcampus.myflat.entities.Defect;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.DefectRepository;
import fhcampus.myflat.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {

    private final ApartmentRepository apartmentRepository;
    private final DefectRepository defectRepository;
    private final UserService userService;

    @Override
    public void reportDefect(DefectDto defectDto, MultipartFile image) throws IOException {
        User user = userService.getCurrentUser();
        Apartment apartment = apartmentRepository.findById(defectDto.getApartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Apartment does not exist."));

        Defect newDefect = new Defect(defectDto, image, user, apartment);
        defectRepository.save(newDefect);
    }
}
