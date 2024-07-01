package fhcampus.myflat.DtoTest;

import fhcampus.myflat.dtos.BookApartmentDto;
import fhcampus.myflat.enums.BookApartmentStatus;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookApartmentDtoTest {

    @Test
    public void testGettersAndSetters() {
        BookApartmentDto bookApartmentDto = new BookApartmentDto();
        Date date = new Date();
        bookApartmentDto.setId(1L);
        bookApartmentDto.setFromDate(date);
        bookApartmentDto.setToDate(date);
        bookApartmentDto.setAmount(1000);
        bookApartmentDto.setUserId(1L);
        bookApartmentDto.setPropertyId(1L);
        bookApartmentDto.setTop(1L);
        bookApartmentDto.setBookApartmentStatus(BookApartmentStatus.FORMERTENANT);

        assertEquals(1L, bookApartmentDto.getId());
        assertEquals(date, bookApartmentDto.getFromDate());
        assertEquals(date, bookApartmentDto.getToDate());
        assertEquals(1000, bookApartmentDto.getAmount());
        assertEquals(1L, bookApartmentDto.getUserId());
        assertEquals(1L, bookApartmentDto.getPropertyId());
        assertEquals(1L, bookApartmentDto.getTop());
        assertEquals(BookApartmentStatus.FORMERTENANT, bookApartmentDto.getBookApartmentStatus());
    }
}