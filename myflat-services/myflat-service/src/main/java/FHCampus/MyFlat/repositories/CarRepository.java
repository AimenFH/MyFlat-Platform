package FHCampus.MyFlat.repositories;

import FHCampus.MyFlat.entities.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Apartment, Long> {
}
