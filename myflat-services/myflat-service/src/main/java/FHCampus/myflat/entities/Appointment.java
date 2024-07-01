package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.AppointmentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    public AppointmentDto appointmentDto() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(this.id);
        appointmentDto.setTitle(this.title);
        appointmentDto.setDescription(this.description);
        appointmentDto.setDate(this.date);
        return appointmentDto;
    }
}
