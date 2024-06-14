package fhcampus.myflat.entities;
import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.enums.DefectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;


@Entity
@Data
@Table(name = "defect")
public class Defect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Description cannot be blank")
    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @Past(message = "timestamp cannot be blank")
    @NonNull
    @Column(name = "Timestamp", nullable = false)
    private Date timestamp;


    @Column(columnDefinition = "longblob")
    private byte[] image;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apartment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Apartment apartment;

     private DefectStatus status;

    public Defect() {

    }

    public DefectDto Defect() {
        DefectDto defectDto = new DefectDto();
        defectDto.setId(id);
        defectDto.setDescription(description);
        defectDto.setTimestamp(timestamp);
        defectDto.setReturnedImage(image);
        defectDto.setUserId(user.getId());
        defectDto.setApartmentId(apartment.getId());
        return defectDto;
    }

    }
