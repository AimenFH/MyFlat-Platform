package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.PropertyDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyDtoTest {

    @Test
    public void testGettersAndSetters() {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setId(1L);
        propertyDto.setPropertyName("My Property");
        propertyDto.setPropertyAddress("123 Main St");
        propertyDto.setNumberOfFloors(5);
        propertyDto.setNumberOfApartments(20);

        assertEquals(1L, propertyDto.getId());
        assertEquals("My Property", propertyDto.getPropertyName());
        assertEquals("123 Main St", propertyDto.getPropertyAddress());
        assertEquals(5, propertyDto.getNumberOfFloors());
        assertEquals(20, propertyDto.getNumberOfApartments());
    }
}