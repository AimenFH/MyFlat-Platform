package fhcampus.myflat.entities;

import fhcampus.myflat.dtos.KeyManagementDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KeyManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer propertyId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer apartmentId;

    @Column(nullable = false)
    private Date issuanceDate;

    @Column(nullable = false)
    private Date redemptionDate;

    @Column(nullable = false)
    private boolean replacementRequested;

    @Column(nullable = false)
    private Integer keysNumber;

    public KeyManagementDto getKeyManagementDto() {
        KeyManagementDto keyManagementDto = new KeyManagementDto();
        keyManagementDto.setId(id);
        keyManagementDto.setPropertyId(propertyId);
        keyManagementDto.setUserId(userId);
        keyManagementDto.setApartmentId(apartmentId);
        keyManagementDto.setIssuanceDate(issuanceDate);
        keyManagementDto.setRedemptionDate(redemptionDate);
        keyManagementDto.setReplacementRequested(replacementRequested);
        keyManagementDto.setKeysNumber(keysNumber);
        return keyManagementDto;
    }
}