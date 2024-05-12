package FHCampus.MyFlat.repositories;

import FHCampus.MyFlat.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
