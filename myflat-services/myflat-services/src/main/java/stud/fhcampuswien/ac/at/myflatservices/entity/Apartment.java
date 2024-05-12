package stud.fhcampuswien.ac.at.myflatservices.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"property_id"})
})
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "number cannot be blank")
    @Column(name = "number", nullable = false)
    private int number;

    @NotBlank(message = "floor cannot be blank")
    @Column(name = "floor", nullable = false)
    private int floor;

    @NotBlank(message = "floor cannot be blank")
    @Column(name = "area", nullable = false)
    private float area;

    @JsonIgnore
    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<TenantApartment> tenantApartment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;
}


