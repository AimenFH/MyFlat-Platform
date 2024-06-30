package fhcampus.myflat.dtos;

public class NoNotificationsFoundException extends RuntimeException {
    public NoNotificationsFoundException(String message) {
        super(message);
    }
}
