package fhcampus.myflat.servicesTest;

import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.entities.Apartment;
import fhcampus.myflat.entities.Defect;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.DefectRepository;
import fhcampus.myflat.servicesTest.defect.DefectServiceImpl;
import fhcampus.myflat.servicesTest.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class DefectServiceImplTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private DefectRepository defectRepository;

    @Mock
    private UserService userService;

    @Mock
    private MultipartFile image;

    @Mock
    private DefectDto defectDto;

    @InjectMocks
    private DefectServiceImpl defectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reportDefectSuccessfully() throws IOException {
        User user = new User();
        Apartment apartment = new Apartment();
        when(userService.getCurrentUser()).thenReturn(user);
        when(apartmentRepository.findById(any())).thenReturn(Optional.of(apartment));

        defectService.reportDefect(defectDto, image);

        verify(defectRepository, times(1)).save(any(Defect.class));
    }

    @Test
    void reportDefectApartmentDoesNotExist() {
        User user = new User();
        when(userService.getCurrentUser()).thenReturn(user);
        when(apartmentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> defectService.reportDefect(defectDto, image));

        verify(defectRepository, times(0)).save(any(Defect.class));
    }
}