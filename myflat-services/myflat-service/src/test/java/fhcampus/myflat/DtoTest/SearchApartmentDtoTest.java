package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.SearchApartmentDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchApartmentDtoTest {

    @Test
    public void testGettersAndSetters() {
        SearchApartmentDto searchApartmentDto = new SearchApartmentDto(1, 2, 100.0f, 5000);

        assertEquals(1, searchApartmentDto.getNumber());
        assertEquals(2, searchApartmentDto.getFloor());
        assertEquals(100.0f, searchApartmentDto.getArea());
        assertEquals(5000, searchApartmentDto.getPrice());
    }
}