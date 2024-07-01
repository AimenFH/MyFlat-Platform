package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.DistributionRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistributionRequestDtoTest {

    @Test
    public void testGettersAndSetters() {
        DistributionRequestDto distributionRequestDto = new DistributionRequestDto();
        distributionRequestDto.setMassage("Test message");
        byte[] document = new byte[10];
        distributionRequestDto.setDocument(document);
        distributionRequestDto.setBuildingId(1);
        distributionRequestDto.setTopId(1);
        distributionRequestDto.setTitle("Test Title");

        assertEquals("Test message", distributionRequestDto.getMassage());
        assertEquals(document, distributionRequestDto.getDocument());
        assertEquals(1, distributionRequestDto.getBuildingId());
        assertEquals(1, distributionRequestDto.getTopId());
        assertEquals("Test Title", distributionRequestDto.getTitle());
    }
}