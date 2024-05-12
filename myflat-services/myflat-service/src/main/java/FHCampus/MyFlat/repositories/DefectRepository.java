package FHCampus.MyFlat.repositories;

import FHCampus.MyFlat.entities.Defect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefectRepository extends JpaRepository<Defect, Long> {
}
