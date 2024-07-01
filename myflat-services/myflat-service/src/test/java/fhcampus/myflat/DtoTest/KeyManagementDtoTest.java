package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.KeyManagementDto;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class KeyManagementDtoTest {

    @Test
    public void testGettersAndSetters() {
        KeyManagementDto keyManagementDto = new KeyManagementDto();
        Date date = new Date();
        keyManagementDto.setId(1L);
        keyManagementDto.setPropertyId(101);
        keyManagementDto.setUserId(1);
        keyManagementDto.setApartmentId(1);
        keyManagementDto.setIssuanceDate(date);
        keyManagementDto.setRedemptionDate(date);
        keyManagementDto.setReplacementRequested(false);
        keyManagementDto.setKeysNumber(2);

        assertEquals(1L, keyManagementDto.getId());
        assertEquals(101, keyManagementDto.getPropertyId());
        assertEquals(1, keyManagementDto.getUserId());
        assertEquals(1, keyManagementDto.getApartmentId());
        assertEquals(date, keyManagementDto.getIssuanceDate());
        assertEquals(date, keyManagementDto.getRedemptionDate());
        assertFalse(keyManagementDto.isReplacementRequested());
        assertEquals(2, keyManagementDto.getKeysNumber());
    }
}