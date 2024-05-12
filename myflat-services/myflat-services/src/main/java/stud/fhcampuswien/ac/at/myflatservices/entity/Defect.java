package stud.fhcampuswien.ac.at.myflatservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "defects", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenant_id", "apartment_id", "property_id"})
})
public class Defect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Description cannot be blank")
    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "Status cannot be blank")
    @NonNull
    @Column(name = "status", nullable = false)
    private String status;

    @Past(message = "timestamp cannot be blank")
    @NonNull
    @Column(name = "Timestamp", nullable = false)
    private Date timestamp;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Tenant tenant;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;
}