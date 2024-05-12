package stud.fhcampuswien.ac.at.myflatservices.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stud.fhcampuswien.ac.at.myflatservices.entity.Apartment;
import stud.fhcampuswien.ac.at.myflatservices.entity.Defect;
import stud.fhcampuswien.ac.at.myflatservices.entity.Tenant;
import stud.fhcampuswien.ac.at.myflatservices.exception.DefectNotFoundException;
import stud.fhcampuswien.ac.at.myflatservices.repository.ApartmentRepository;
import stud.fhcampuswien.ac.at.myflatservices.repository.DefectRepository;
import stud.fhcampuswien.ac.at.myflatservices.repository.TenantRepository;
import stud.fhcampuswien.ac.at.myflatservices.service.base.DefectService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefectServiceImpl implements DefectService {
    DefectRepository defectsRepository;
    ApartmentRepository apartmentRepository;
    TenantRepository tenantRepository;

    @Override
    public List<Defect> getAllDefects() {
        return (List<Defect>) defectsRepository.findAll();
    }

    @Override
    public Defect getDefect(Long tenantId, Long apartmentId) {
        Optional<Defect> defect = defectsRepository.findByTenantIdAndApartmentId(tenantId, apartmentId);
        return unwrapDefect(defect,tenantId, tenantId );
    }

    @Override
    public Defect saveDefect(Defect defect, Long tenantId, Long apartmentId) {
        Tenant tenant = TenantServiceImpl.unwrapTenant(tenantRepository.findById(tenantId), tenantId);
        Apartment apartment = ApartmentServiceImpl.unwrapApartment(apartmentRepository.findById(apartmentId), apartmentId);
        if (!tenant.getTenantApartment().contains(apartment)) throw new IllegalStateException("go away");
        defect.setTenant(tenant);
        defect.setApartment(apartment);
        return defectsRepository.save(defect);
    }

    @Override
    public Defect updateDefect(String description, String status, Long tenantId, Long apartmentId) {
        Optional<Defect> defect = defectsRepository.findByTenantIdAndApartmentId(tenantId, apartmentId);
        unwrapDefect(defect, tenantId, apartmentId).setDescription(description);
        unwrapDefect(defect, tenantId, apartmentId).setStatus(status);
        return null;
    }

    @Override
    public void deleteDefect(Long tenantId, Long apartmentId) {
        defectsRepository.deleteByTenantIdAndApartmentId(tenantId, apartmentId);
    }
    static Defect unwrapDefect(Optional<Defect> entity, Long tenantId, Long apartmentId) {
        if (entity.isPresent()) return entity.get();
        else throw new DefectNotFoundException(tenantId, apartmentId);
    }
}
