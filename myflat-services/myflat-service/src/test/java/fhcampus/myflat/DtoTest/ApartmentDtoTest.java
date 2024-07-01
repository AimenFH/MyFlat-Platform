package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.ApartmentDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApartmentDtoTest {

    @Test
    public void testGettersAndSetters() {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setId(1L);
        apartmentDto.setNumber(101);
        apartmentDto.setFloor(1);
        apartmentDto.setArea(100.0f);
        apartmentDto.setPrice(1000);
        apartmentDto.setPropertyId(1L);

        assertEquals(1L, apartmentDto.getId());
        assertEquals(101, apartmentDto.getNumber());
        assertEquals(1, apartmentDto.getFloor());
        assertEquals(100.0f, apartmentDto.getArea());
        assertEquals(1000, apartmentDto.getPrice());
        assertEquals(1L, apartmentDto.getPropertyId());
    }
}