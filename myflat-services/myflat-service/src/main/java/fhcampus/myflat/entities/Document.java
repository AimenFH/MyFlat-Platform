package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.DocumentDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private User user;

    private String title;

    @Column(nullable = false, columnDefinition="LONGBLOB")
    @Lob
    private byte[] content;

    private boolean isArchived;


    public DocumentDto documentDto() {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(id);
        documentDto.setApartmentId(apartment.getId());
        documentDto.setTitle(title);
        documentDto.setContent(content);
        documentDto.setArchived(isArchived);
        documentDto.setUserId(user.getId());
        return documentDto;
    }
}