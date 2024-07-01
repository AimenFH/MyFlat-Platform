package fhcampus.myflat.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingResultTest {

    @Test
    public void testGettersAndSetters() {
        BookingResult bookingResult = new BookingResult();
        bookingResult.setSuccess(true);
        bookingResult.setMessage("Booking successful");

        assertEquals(true, bookingResult.isSuccess());
        assertEquals("Booking successful", bookingResult.getMessage());
    }
}