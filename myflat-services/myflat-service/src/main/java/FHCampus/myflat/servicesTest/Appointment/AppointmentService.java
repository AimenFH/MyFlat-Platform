package fhcampus.myflat.servicesTest.Appointment;

import fhcampus.myflat.dtos.AppointmentDto;
import fhcampus.myflat.entities.Appointment;
import fhcampus.myflat.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
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

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(Appointment::appointmentDto)
                .sorted(Comparator.comparing(AppointmentDto::getDate))
                .collect(Collectors.toList());
    }
}