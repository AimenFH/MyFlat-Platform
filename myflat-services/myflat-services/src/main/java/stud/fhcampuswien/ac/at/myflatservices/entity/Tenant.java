package stud.fhcampuswien.ac.at.myflatservices.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "firstName cannot be blank")
    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "password cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "lastName cannot be blank")
    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "contactNumber cannot be blank")
    @NonNull
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @NotBlank(message = "DateOfBirth cannot be blank")
    @NonNull
    @Column(name = "date_of-birth", nullable = false)
    private Date dateOfBirth;

    @NotBlank(message = "Email cannot be blank")
    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<Defect> defects;

    @JsonIgnore
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<TenantApartment> tenantApartment;
}