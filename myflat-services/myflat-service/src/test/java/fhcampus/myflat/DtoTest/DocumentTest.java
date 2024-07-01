package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.DocumentDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DocumentTest {

    @Test
    public void testGettersAndSetters() {
        Document document = new Document();
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        User user = new User();
        user.setId(1L);
        byte[] content = new byte[10];

        document.setId(1L);
        document.setApartment(apartment);
        document.setUser(user);
        document.setTitle("Test Document");
        document.setContent(content);
        document.setArchived(false);

        assertEquals(1L, document.getId());
        assertEquals(apartment, document.getApartment());
        assertEquals(user, document.getUser());
        assertEquals("Test Document", document.getTitle());
        assertEquals(content, document.getContent());
        assertFalse(document.isArchived());

        DocumentDto documentDto = document.documentDto();
        assertEquals(document.getId(), documentDto.getId());
        assertEquals(document.getApartment().getId(), documentDto.getApartmentId());
        assertEquals(document.getTitle(), documentDto.getTitle());
        assertEquals(document.getContent(), documentDto.getContent());
        assertEquals(document.isArchived(), documentDto.isArchived());
        assertEquals(document.getUser().getId(), documentDto.getUserId());
    }
}