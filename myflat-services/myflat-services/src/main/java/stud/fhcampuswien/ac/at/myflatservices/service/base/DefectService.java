package stud.fhcampuswien.ac.at.myflatservices.service.base;

import stud.fhcampuswien.ac.at.myflatservices.entity.Defect;

import java.util.List;

public interface DefectService {
    Defect saveDefect(Defect defect, Long tenantId, Long apartmentId);


    Defect updateDefect(String description, String status, Long tenantId, Long apartmentId);

    List<Defect> getAllDefects();

    Defect getDefect(Long tenantId, Long apartmentId);

    void deleteDefect(Long tenantId, Long apartmentId);
}
