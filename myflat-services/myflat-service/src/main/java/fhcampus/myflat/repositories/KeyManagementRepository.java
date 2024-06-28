package fhcampus.myflat.repositories;

import fhcampus.myflat.entities.KeyManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyManagementRepository extends JpaRepository<KeyManagement, Long> {
    List<KeyManagement> findAllByUserId(Integer userId);
}