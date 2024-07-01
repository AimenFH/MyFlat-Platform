package fhcampus.myflat.dtos;

import java.util.List;
import fhcampus.myflat.entities.Message;
import fhcampus.myflat.entities.User;
import jakarta.persistence.*;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "conversation_users",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> messages;

    // Getters and Setters

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}