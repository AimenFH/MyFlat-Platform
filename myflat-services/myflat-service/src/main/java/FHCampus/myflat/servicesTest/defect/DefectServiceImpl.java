package fhcampus.myflat.servicesTest.defect;

import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.entities.Defect;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.DefectRepository;
import fhcampus.myflat.entities.Apartment;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.servicesTest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<DefectDto> getAllDefects() {
        return defectRepository.findAll().stream()
                .map(DefectDto::new)
                .collect(Collectors.toList());
    }



    @Override
    public List<DefectDto> getDefectsByUserId(Long userId) {
        List<Defect> defects = defectRepository.findByUserId(userId);
        return defects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DefectDto convertToDto(Defect defect) {
        DefectDto defectDto = new DefectDto();
        defectDto.setId(defect.getId());
        defectDto.setDescription(defect.getDescription());
        defectDto.setTimestamp(defect.getTimestamp());
        defectDto.setUserId(defect.getUser().getId());
        defectDto.setApartmentId(defect.getApartment().getId());
        defectDto.setStatus(defect.getStatus());
        defectDto.setCategory(defect.getCategory());
        defectDto.setLocation(defect.getLocation());
        return defectDto;
    }


}
