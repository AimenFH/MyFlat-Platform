package fhcampus.myflat.servicesTest.key;

import fhcampus.myflat.dtos.KeyManagementDto;
import fhcampus.myflat.entities.KeyManagement;
import fhcampus.myflat.repositories.KeyManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeyManagementService {

    private final KeyManagementRepository keyManagementRepository;




    public boolean createKeyManagement(KeyManagementDto keyManagementDto) {
        try {
            KeyManagement keyManagement = new KeyManagement();
            keyManagement.setPropertyId(keyManagementDto.getPropertyId());
            keyManagement.setUserId(keyManagementDto.getUserId());
            keyManagement.setApartmentId(keyManagementDto.getApartmentId());
            keyManagement.setIssuanceDate(keyManagementDto.getIssuanceDate());
            keyManagement.setRedemptionDate(keyManagementDto.getRedemptionDate());
            keyManagement.setReplacementRequested(keyManagementDto.isReplacementRequested());
            keyManagement.setKeysNumber(keyManagementDto.getKeysNumber());
            keyManagementRepository.save(keyManagement);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<KeyManagementDto> getAllKeyManagements() {
        return keyManagementRepository.findAll().stream()
                .map(KeyManagement::getKeyManagementDto)
                .collect(Collectors.toList());
    }

    public Optional<List<KeyManagementDto>> getKeyManagementByUserId(Integer userId) {
        List<KeyManagement> keyManagements = keyManagementRepository.findAllByUserId(userId);
        if (keyManagements.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(keyManagements.stream()
                .map(KeyManagement::getKeyManagementDto)
                .collect(Collectors.toList()));
    }


}