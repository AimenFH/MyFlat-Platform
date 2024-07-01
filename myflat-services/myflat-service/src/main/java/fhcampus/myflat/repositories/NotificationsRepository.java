package fhcampus.myflat.repositories;

import fhcampus.myflat.entities.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findByBuildingIdAndTopId(Integer buildingId, Integer topId);

    List<Notifications> findByBuildingId(Integer buildingId);

    List<Notifications> findByTopId(Integer topId);



}

