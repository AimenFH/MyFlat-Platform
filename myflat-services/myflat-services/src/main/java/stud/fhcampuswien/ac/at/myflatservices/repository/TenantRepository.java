package stud.fhcampuswien.ac.at.myflatservices.repository;

import stud.fhcampuswien.ac.at.myflatservices.entity.Tenant;
import org.springframework.data.repository.CrudRepository;
public interface TenantRepository extends CrudRepository<Tenant, Long> {
}
