package stud.fhcampuswien.ac.at.myflatservices.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(long id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }

}







