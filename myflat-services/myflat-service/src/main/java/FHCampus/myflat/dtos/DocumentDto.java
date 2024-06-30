package fhcampus.myflat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private Long id;
    private Long apartmentId;
    private String title;
    private String content;
    private boolean isArchived;
    private Long userId;
}