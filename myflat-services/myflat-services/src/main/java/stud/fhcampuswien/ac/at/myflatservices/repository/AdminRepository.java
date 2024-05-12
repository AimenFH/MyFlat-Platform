package stud.fhcampuswien.ac.at.myflatservices.repository;

import stud.fhcampuswien.ac.at.myflatservices.entity.Admin;
import org.springframework.data.repository.CrudRepository;
public interface AdminRepository extends CrudRepository<Admin, Long> {
  //  AdminRepository findByUsername(String agentName);
}
