package fhcampus.myflat.exceptionsTest;

import fhcampus.myflat.exceptions.NoNotificationsFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoNotificationsFoundExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "No notifications found";
        NoNotificationsFoundException exception = new NoNotificationsFoundException(message);

        assertEquals(message, exception.getMessage());
    }
}