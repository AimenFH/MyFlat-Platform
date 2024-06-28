package fhcampus.myflat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyManagementDto {

    private Long id;
    private Integer propertyId;
    private Integer userId;
    private  Integer apartmentId;
    private Date issuanceDate;
    private Date redemptionDate;
    private boolean replacementRequested;
    private Integer keysNumber;


}