package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.enums.DefectCategory;
import fhcampus.myflat.enums.DefectLocation;
import fhcampus.myflat.enums.DefectStatus;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefectDtoTest {

    @Test
    public void testGettersAndSetters() {
        DefectDto defectDto = new DefectDto();
        Date date = new Date();
        defectDto.setId(1L);
        defectDto.setDescription("Leaky faucet");
        defectDto.setTimestamp(date);
        defectDto.setUserId(1L);
        defectDto.setApartmentId(1L);
        defectDto.setStatus(DefectStatus.OPEN);
        defectDto.setCategory(DefectCategory.ELECTRICAL);
        defectDto.setLocation(DefectLocation.COMMON_AREA);

        assertEquals(1L, defectDto.getId());
        assertEquals("Leaky faucet", defectDto.getDescription());
        assertEquals(date, defectDto.getTimestamp());
        assertEquals(1L, defectDto.getUserId());
        assertEquals(1L, defectDto.getApartmentId());
        assertEquals(DefectStatus.OPEN, defectDto.getStatus());
        assertEquals(DefectCategory.ELECTRICAL, defectDto.getCategory());
        assertEquals(DefectLocation.COMMON_AREA, defectDto.getLocation());
    }
}