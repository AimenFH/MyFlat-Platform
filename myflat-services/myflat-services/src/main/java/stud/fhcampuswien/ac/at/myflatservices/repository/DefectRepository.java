package stud.fhcampuswien.ac.at.myflatservices.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import stud.fhcampuswien.ac.at.myflatservices.entity.Defect;

import java.util.Optional;

public interface DefectRepository extends CrudRepository<Defect, Long> {
    Optional<Defect> findByTenantIdAndApartmentId(Long tenantId, Long  apartmentId);
    @Transactional
     void deleteByTenantIdAndApartmentId(Long tenantId, Long apartmentId);
}
