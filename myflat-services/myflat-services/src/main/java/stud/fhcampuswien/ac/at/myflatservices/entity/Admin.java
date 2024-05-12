package stud.fhcampuswien.ac.at.myflatservices.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "AgentName cannot be blank")
    @NonNull
    @Column(name = "agent_name", nullable = false)
    private String agentName;

    @NotBlank(message = "password cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "AgentEmail cannot be blank")
    @NonNull
    @Column(name = "agentEmail", nullable = false)
    private String agentEmail;

    @NotBlank(message = "AgentNumber cannot be blank")
    @NonNull
    @Column(name = "agentNumber", nullable = false)
    private String agentNumber;

    @NotBlank(message = "AgentPhoneNumber cannot be blank")
    @Column(name = "agentPhoneNumber", nullable = false)
    private int agentPhoneNumber;

    @NotBlank(message = "isSuperAgent cannot be blank")
    @Column(name = "is_super_agent", nullable = false)
    private boolean isSuperAgent;
}
