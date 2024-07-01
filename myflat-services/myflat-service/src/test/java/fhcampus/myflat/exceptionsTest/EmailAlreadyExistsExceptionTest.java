package fhcampus.myflat.exceptionsTest;

import fhcampus.myflat.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailAlreadyExistsExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "Email already exists";
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(message);

        assertEquals(message, exception.getMessage());
    }
}