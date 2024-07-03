package fhcampus.myflat.services.Appointment;

import fhcampus.myflat.dtos.AppointmentDto;
import fhcampus.myflat.entities.Appointment;
import fhcampus.myflat.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing appointments.
 * Provides functionality to create, delete, and retrieve appointments.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * Creates a new appointment based on the provided AppointmentDto.
     * @param appointmentDto The data transfer object containing appointment details.
     * @return boolean indicating the success or failure of the appointment creation.
     */
    public boolean createAppointment(AppointmentDto appointmentDto) {
        try {
            Appointment appointment = new Appointment();
            appointment.setTitle(appointmentDto.getTitle());
            appointment.setDescription(appointmentDto.getDescription());
            appointment.setDate(appointmentDto.getDate());
            appointmentRepository.save(appointment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes an appointment by its ID.
     * @param id The ID of the appointment to be deleted.
     */
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    /**
     * Retrieves all appointments, sorted by date.
     * @return A list of AppointmentDto representing all appointments.
     */
    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(Appointment::appointmentDto)
                .sorted(Comparator.comparing(AppointmentDto::getDate))
                .collect(Collectors.toList());
    }
}