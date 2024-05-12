package stud.fhcampuswien.ac.at.myflatservices.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"property_management_id"})
})
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "propertyName cannot be blank")
    @NonNull
    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @NotBlank(message = "propertyAddress cannot be blank")
    @NonNull
    @Column(name = "property_address", nullable = false)
    private String propertyAddress;

    @NotBlank(message = "numberOfFloors cannot be blank")
    @Column(name = "number_of_floors", nullable = false)
    private int numberOfFloors;

    @NotBlank(message = "numberOfApartments cannot be blank")
    @Column(name = "number_of_apartments", nullable = false)
    private int numberOfApartments;

    @NotBlank(message = "numberOfActiveApartments cannot be blank")
    @Column(name = "number_of_active_apartments", nullable = false)
    private int numberOfActiveApartments;

    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Apartment> apartments;

    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Defect> defects;
}
