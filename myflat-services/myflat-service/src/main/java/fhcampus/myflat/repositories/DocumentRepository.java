package fhcampus.myflat.repositories;

import fhcampus.myflat.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}