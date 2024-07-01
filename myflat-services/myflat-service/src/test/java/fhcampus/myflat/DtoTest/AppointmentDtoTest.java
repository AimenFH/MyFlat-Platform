package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.AppointmentDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppointmentDtoTest {

    @Test
    public void testGettersAndSetters() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(1L);
        appointmentDto.setTitle("Meeting");
        appointmentDto.setDescription("Project meeting");
        LocalDateTime now = LocalDateTime.now();
        appointmentDto.setDate(now);

        assertEquals(1L, appointmentDto.getId());
        assertEquals("Meeting", appointmentDto.getTitle());
        assertEquals("Project meeting", appointmentDto.getDescription());
        assertEquals(now, appointmentDto.getDate());
    }
}