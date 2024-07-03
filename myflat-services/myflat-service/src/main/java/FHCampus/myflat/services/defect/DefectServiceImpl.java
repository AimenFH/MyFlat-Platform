package fhcampus.myflat.services.defect;

import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.entities.Defect;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.DefectRepository;
import fhcampus.myflat.entities.Apartment;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for defect-related operations.
 * This service provides functionality to report defects, retrieve all defects, and retrieve defects by user ID.
 */
@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {

    private final ApartmentRepository apartmentRepository;
    private final DefectRepository defectRepository;
    private final UserService userService;

    /**
     * Reports a defect associated with an apartment.
     * The defect is reported by the current user and includes an optional image.
     *
     * @param defectDto The defect data transfer object containing defect details.
     * @param image The image file of the defect, if available.
     * @throws IOException If an error occurs during image processing.
     */
    @Override
    public void reportDefect(DefectDto defectDto, MultipartFile image) throws IOException {
        User user = userService.getCurrentUser();
        Apartment apartment = apartmentRepository.findById(defectDto.getApartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Apartment does not exist."));

        Defect newDefect = new Defect(defectDto, image, user, apartment);
        defectRepository.save(newDefect);
    }

    /**
     * Retrieves all reported defects.
     *
     * @return A list of DefectDto objects representing all reported defects.
     */
    @Override
    public List<DefectDto> getAllDefects() {
        return defectRepository.findAll().stream()
                .map(DefectDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves defects reported by a specific user.
     *
     * @param userId The ID of the user whose defects are to be retrieved.
     * @return A list of DefectDto objects representing the defects reported by the specified user.
     */
    @Override
    public List<DefectDto> getDefectsByUserId(Long userId) {
        List<Defect> defects = defectRepository.findByUserId(userId);
        return defects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Defect entity to a DefectDto object.
     *
     * @param defect The Defect entity to be converted.
     * @return The DefectDto object containing the defect details.
     */
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
        defectDto.setImage(defect.getImage());
        return defectDto;
    }
}