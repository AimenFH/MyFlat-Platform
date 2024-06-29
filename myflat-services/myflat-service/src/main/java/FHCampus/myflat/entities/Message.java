package fhcampus.myflat.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Lob
    private byte[] document;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
