package stud.fhcampuswien.ac.at.myflatservices.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tenant_apartment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenant_id", "apartment_id"})
})
public class TenantApartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Tenant tenant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @NotBlank(message = "active cannot be null")
    @Column(name = "active", nullable = false)
    private boolean active;
}

