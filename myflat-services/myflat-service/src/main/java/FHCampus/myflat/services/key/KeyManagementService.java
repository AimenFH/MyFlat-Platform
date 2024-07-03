package fhcampus.myflat.services.key;

import fhcampus.myflat.dtos.KeyManagementDto;
import fhcampus.myflat.entities.KeyManagement;
import fhcampus.myflat.repositories.KeyManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing key-related operations.
 * Provides functionality to create, retrieve all, and retrieve key management entries by user ID.
 */
@Service
@RequiredArgsConstructor
public class KeyManagementService {

    private final KeyManagementRepository keyManagementRepository;

    /**
     * Creates a new key management entry in the repository.
     * The entry is created based on the provided KeyManagementDto.
     *
     * @param keyManagementDto Data transfer object containing key management details.
     * @return boolean True if the key management entry is successfully created, false otherwise.
     */
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

    /**
     * Retrieves all key management entries from the repository.
     *
     * @return List<KeyManagementDto> A list of key management DTOs.
     */
    public List<KeyManagementDto> getAllKeyManagements() {
        return keyManagementRepository.findAll().stream()
                .map(KeyManagement::getKeyManagementDto)
                .toList();
    }

    /**
     * Retrieves key management entries for a specific user ID.
     *
     * @param userId The ID of the user for whom to retrieve key management entries.
     * @return Optional<List<KeyManagementDto>> An optional containing a list of key management DTOs if found, or empty otherwise.
     */
    public Optional<List<KeyManagementDto>> getKeyManagementByUserId(Integer userId) {
        List<KeyManagement> keyManagements = keyManagementRepository.findAllByUserId(userId);
        if (keyManagements.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(keyManagements.stream()
                .map(KeyManagement::getKeyManagementDto)
                .toList());
    }
}