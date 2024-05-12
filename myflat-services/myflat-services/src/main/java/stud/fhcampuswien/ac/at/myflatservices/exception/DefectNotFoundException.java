package stud.fhcampuswien.ac.at.myflatservices.exception;

public class DefectNotFoundException extends RuntimeException {

    public DefectNotFoundException(Long tenantId, Long apartmentId) {
        super("The defect with Tenant id: '" + tenantId + "' and Apartment id: '" + apartmentId + "' does not exist in our records");
    }
}
