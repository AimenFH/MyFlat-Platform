package fhcampus.myflat.services.propertymanagement;

import fhcampus.myflat.dtos.*;
import fhcampus.myflat.entities.Apartment;
import fhcampus.myflat.entities.BookApartment;
import fhcampus.myflat.entities.Property;
import fhcampus.myflat.entities.User;
import fhcampus.myflat.enums.BookApartmentStatus;
import fhcampus.myflat.repositories.ApartmentRepository;
import fhcampus.myflat.repositories.BookApartmentRepository;
import fhcampus.myflat.repositories.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PropertyManagementServiceImplTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private BookApartmentRepository bookApartmentRepository;

    @InjectMocks
    private PropertyManagementServiceImpl propertyManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postPropertySuccessfully() {
        Property mockProperty = new Property(1L, "Property Name", "Property Address",
                3, 9);
        PropertyDto propertyDto = new PropertyDto(1L, "Property Name", "Property Address",
                3, 9);
        when(propertyRepository.save(any(Property.class))).thenReturn(mockProperty);
        assertTrue(propertyManagementService.postProperty(propertyDto));
    }

    @Test
    void postPropertyFailure() {
        PropertyDto propertyDto = new PropertyDto(1L, "Property Name", "Property Address",
                3, 9);
        when(propertyRepository.save(any(Property.class))).thenThrow(new RuntimeException());
        assertFalse(propertyManagementService.postProperty(propertyDto));
    }

    @Test
    void postApartmentSuccessfully() {
        ApartmentDto apartmentDto = new ApartmentDto(1L, 1, 1, 100f, 500, 1L);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(new Property()));
        when(apartmentRepository.save(any(Apartment.class))).thenReturn(new Apartment());
        assertTrue(propertyManagementService.postApartment(apartmentDto));
    }

    @Test
    void postApartmentFailure() {
        ApartmentDto apartmentDto = new ApartmentDto(1L, 1, 1, 100f, 500, 1L);
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());
        assertFalse(propertyManagementService.postApartment(apartmentDto));
    }

    @Test
    void getAllApartmentsReturnsExpectedList() {
        Property mockProperty = new Property(1L, "Property Name", "Property Address",
                3, 9);
        Apartment mockApartment1 = new Apartment(1L, 1, 1, 100f, 500, mockProperty);
        Apartment mockApartment2 = new Apartment(2L, 2, 1, 100f, 500, mockProperty);
        when(apartmentRepository.findAll()).thenReturn(List.of(mockApartment1, mockApartment2));

        List<ApartmentDto> result = propertyManagementService.getAllApartments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockApartment1.getId(), result.get(0).getId());
        assertEquals(mockApartment1.getNumber(), result.get(0).getNumber());
        assertEquals(mockApartment1.getFloor(), result.get(0).getFloor());
        assertEquals(mockApartment1.getArea(), result.get(0).getArea());
        assertEquals(mockApartment1.getPrice(), result.get(0).getPrice());
        assertEquals(mockApartment1.getProperty().getId(), result.get(0).getPropertyId());
        assertEquals(mockApartment2.getId(), result.get(1).getId());
        assertEquals(mockApartment2.getNumber(), result.get(1).getNumber());
        assertEquals(mockApartment2.getFloor(), result.get(1).getFloor());
        assertEquals(mockApartment2.getArea(), result.get(1).getArea());
        assertEquals(mockApartment2.getPrice(), result.get(1).getPrice());
        assertEquals(mockApartment2.getProperty().getId(), result.get(1).getPropertyId());
    }

    @Test
    void getApartmentByIdReturnsExpectedApartment() {
        Property mockProperty = new Property(1L, "Property Name", "Property Address",
                3, 9);
        Apartment mockApartment = new Apartment(1L, 1, 1, 100f, 500, mockProperty);
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(mockApartment));

        ApartmentDto result = propertyManagementService.getApartmentById(1L);

        assertNotNull(result);
        assertEquals(mockApartment.getId(), result.getId());
        assertEquals(mockApartment.getNumber(), result.getNumber());
        assertEquals(mockApartment.getFloor(), result.getFloor());
        assertEquals(mockApartment.getArea(), result.getArea());
        assertEquals(mockApartment.getPrice(), result.getPrice());
        assertEquals(mockApartment.getProperty().getId(), result.getPropertyId());
    }

    @Test
    void getApartmentByIdReturnsNullForMissingApartment() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(propertyManagementService.getApartmentById(1L));
    }

    @Test
    void updateApartmentSuccessfully() {
        Apartment existingApartment = new Apartment(1L, 1, 1, 100f, 500, new Property());
        ApartmentDto apartmentDto = new ApartmentDto(1L, 2, 2, 200f, 1000, 1L);

        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(existingApartment));
        when(apartmentRepository.save(any(Apartment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = propertyManagementService.updateApartment(1L, apartmentDto);

        assertTrue(result);
        assertEquals(apartmentDto.getNumber(), existingApartment.getNumber());
        assertEquals(apartmentDto.getFloor(), existingApartment.getFloor());
        assertEquals(apartmentDto.getArea(), existingApartment.getArea());
        assertEquals(apartmentDto.getPrice(), existingApartment.getPrice());
    }

    @Test
    void updateApartmentFailureDueToMissingApartment() {
        ApartmentDto apartmentDto = new ApartmentDto();
        when(apartmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertFalse(propertyManagementService.updateApartment(1L, apartmentDto));
    }

    @Test
    void changeBookingStatusSuccessfully() {
        Long bookingId = 1L;
        Integer status = 0;
        BookApartment mockBooking = new BookApartment();
        when(bookApartmentRepository.findById(bookingId)).thenReturn(Optional.of(mockBooking));
        when(bookApartmentRepository.save(any(BookApartment.class))).thenReturn(new BookApartment());

        boolean result = propertyManagementService.changeBookingStatus(bookingId, status);

        assertTrue(result);
        assertEquals(BookApartmentStatus.CURRENTENANT, mockBooking.getBookApartmentStatus());
        verify(bookApartmentRepository).save(mockBooking);
    }

    @Test
    void changeBookingStatusFailureDueToMissingBooking() {
        Long bookingId = 1L;
        Integer status = 0;
        when(bookApartmentRepository.findById(bookingId)).thenReturn(Optional.empty());

        boolean result = propertyManagementService.changeBookingStatus(bookingId, status);

        assertFalse(result);
        verify(bookApartmentRepository, never()).save(any());
    }

    @Test
    void changeBookingStatusReturnsTrueWhenBookingExistsAndStatusIsZero() {
        Long bookingId = 1L;
        Integer status = 0;
        BookApartment mockBooking = new BookApartment();
        when(bookApartmentRepository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        boolean result = propertyManagementService.changeBookingStatus(bookingId, status);

        assertTrue(result);
        assertEquals(BookApartmentStatus.CURRENTENANT, mockBooking.getBookApartmentStatus());
        verify(bookApartmentRepository).save(mockBooking);
    }

    @Test
    void changeBookingStatusReturnsTrueWhenBookingExistsAndStatusIsNotZero() {
        Long bookingId = 1L;
        Integer status = 1;
        BookApartment mockBooking = new BookApartment();
        when(bookApartmentRepository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        boolean result = propertyManagementService.changeBookingStatus(bookingId, status);

        assertTrue(result);
        assertEquals(BookApartmentStatus.FORMERTENANT, mockBooking.getBookApartmentStatus());
        verify(bookApartmentRepository).save(mockBooking);
    }

    @Test
    void changeBookingStatusReturnsFalseWhenBookingDoesNotExist() {
        Long bookingId = 1L;
        Integer status = 0;
        when(bookApartmentRepository.findById(bookingId)).thenReturn(Optional.empty());

        boolean result = propertyManagementService.changeBookingStatus(bookingId, status);

        assertFalse(result);
        verify(bookApartmentRepository, never()).save(any());
    }

    @Test
    void deleteApartmentSuccessfully() {
        Long apartmentId = 1L;
        doNothing().when(apartmentRepository).deleteById(apartmentId);
        propertyManagementService.deleteApartment(apartmentId);
        verify(apartmentRepository, times(1)).deleteById(apartmentId);
    }

    @Test
    void deleteApartmentWithNonExistingId() {
        Long apartmentId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(apartmentRepository).deleteById(apartmentId);
        assertThrows(EmptyResultDataAccessException.class, () -> propertyManagementService.deleteApartment(apartmentId));
        verify(apartmentRepository, times(1)).deleteById(apartmentId);
    }

    @Test
    void getBookingsReturnsExpectedList() {
        BookApartment mockBooking1 = new BookApartment(1L, new Date(), new Date(), 1, new User(),
                new Apartment(), new Property(), BookApartmentStatus.CURRENTENANT);
        BookApartment mockBooking2 = new BookApartment(2L, new Date(), new Date(), 1, new User(),
                new Apartment(), new Property(), BookApartmentStatus.FORMERTENANT);
        when(bookApartmentRepository.findAll()).thenReturn(List.of(mockBooking1, mockBooking2));

        List<BookApartmentDto> result = propertyManagementService.getBookings();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockBooking1.getId(), result.get(0).getId());
        assertEquals(mockBooking1.getBookApartmentStatus(), result.get(0).getBookApartmentStatus());
        assertEquals(mockBooking2.getId(), result.get(1).getId());
        assertEquals(mockBooking2.getBookApartmentStatus(), result.get(1).getBookApartmentStatus());
    }

    @Test
    void getBookingsReturnsEmptyListWhenNoBookings() {
        when(bookApartmentRepository.findAll()).thenReturn(Collections.emptyList());

        List<BookApartmentDto> result = propertyManagementService.getBookings();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchApartmentReturnsExpectedList() {
        SearchApartmentDto searchApartmentDto = new SearchApartmentDto(1, 1, 100f, 500);
        Apartment mockApartment1 = new Apartment(1L, 1, 1, 100f, 500, new Property());
        Apartment mockApartment2 = new Apartment(2L, 2, 1, 100f, 500, new Property());
        when(apartmentRepository.findAll(any(Example.class))).thenReturn(List.of(mockApartment1, mockApartment2));

        ApartmentDtoList result = propertyManagementService.searchApartment(searchApartmentDto);

        assertNotNull(result);
        assertEquals(2, result.getApartmentDtoList().size());
        assertEquals(mockApartment1.getId(), result.getApartmentDtoList().get(0).getId());
        assertEquals(mockApartment2.getId(), result.getApartmentDtoList().get(1).getId());
    }

    @Test
    void searchApartmentReturnsEmptyListWhenNoMatch() {
        SearchApartmentDto searchApartmentDto = new SearchApartmentDto(3, 3, 300f, 1500);
        when(apartmentRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        ApartmentDtoList result = propertyManagementService.searchApartment(searchApartmentDto);

        assertNotNull(result);
        assertTrue(result.getApartmentDtoList().isEmpty());
    }
}