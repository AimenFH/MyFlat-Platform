package fhcampus.myflat.servicesTest;

import fhcampus.myflat.dtos.AppointmentDto;

import fhcampus.myflat.entities.Appointment;
import fhcampus.myflat.repositories.AppointmentRepository;
import fhcampus.myflat.servicesTest.Appointment.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {

    @InjectMocks
    AppointmentService appointmentService;

    @Mock
    AppointmentRepository appointmentRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAppointment() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setTitle("New Appointment");

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(new Appointment());

        boolean isCreated = appointmentService.createAppointment(appointmentDto);

        assertEquals(true, isCreated);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    public void testDeleteAppointment() {
        doNothing().when(appointmentRepository).deleteById(1L);

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}