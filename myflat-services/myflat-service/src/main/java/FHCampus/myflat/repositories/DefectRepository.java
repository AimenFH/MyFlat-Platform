package fhcampus.myflat.repositories;

import fhcampus.myflat.entities.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {
}
