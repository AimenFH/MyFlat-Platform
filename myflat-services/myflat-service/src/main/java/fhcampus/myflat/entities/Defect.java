package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.enums.DefectCategory;
import fhcampus.myflat.enums.DefectLocation;
import fhcampus.myflat.enums.DefectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
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

    @Past(message = "Timestamp cannot be blank")
    @NonNull
    @Column(name = "timestamp", nullable = false)
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

    private DefectCategory category;

    private DefectLocation location;

    private LocalDateTime reportedAt;

    public Defect(DefectDto defectDto, MultipartFile image, User user, Apartment apartment) throws IOException {
        this.description = defectDto.getDescription();
        this.timestamp = defectDto.getTimestamp();
        this.image = image.getBytes();
        this.user = user;
        this.apartment = apartment;
        this.status = defectDto.getStatus();
        this.category = defectDto.getCategory();
        this.location = defectDto.getLocation();
    }
}
