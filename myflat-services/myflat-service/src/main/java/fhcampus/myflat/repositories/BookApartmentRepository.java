package fhcampus.myflat.repositories;

import fhcampus.myflat.entities.Apartment;
import fhcampus.myflat.entities.BookApartment;
import fhcampus.myflat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface BookApartmentRepository extends JpaRepository<BookApartment, Long> {
    List<BookApartment> findAllByUserId(Long userId);

    @Query("SELECT b FROM BookApartment b " +
            "WHERE b.apartment = :apartment " +
            "AND b.toDate >= :fromDate " +
            "AND b.fromDate <= :toDate")
    List<BookApartment> findByApartmentAndToDateGreaterThanEqualAndFromDateLessThanEqual(
            Apartment apartment, Date fromDate, Date toDate);

    @Query("SELECT b FROM BookApartment b WHERE b.user.id = :userId")
    List<BookApartment> findByUserId(@Param("userId") Long userId);

}
