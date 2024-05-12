package stud.fhcampuswien.ac.at.myflatservices.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stud.fhcampuswien.ac.at.myflatservices.entity.Tenant;
import stud.fhcampuswien.ac.at.myflatservices.entity.TenantApartment;
import stud.fhcampuswien.ac.at.myflatservices.exception.EntityNotFoundException;
import stud.fhcampuswien.ac.at.myflatservices.repository.TenantRepository;
import stud.fhcampuswien.ac.at.myflatservices.service.base.TenantService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TenantServiceImpl implements TenantService {

    private TenantRepository tenantRepository;

    @Override
    public Tenant getTenant(Long id) {
        Optional<Tenant> tenant = tenantRepository.findById(id);
        return unwrapTenant(tenant, id);
    }

    @Override
    public List<Tenant> getTenants() {
        return (List<Tenant>) tenantRepository.findAll();
    }

    @Override
    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public void deleteTenant(long id) {
        tenantRepository.deleteById(id);
    }

    @Override
    public List<TenantApartment> tenantApartments(long id) {
        Tenant tenant = getTenant(id);
        return tenant.getTenantApartment();
    }

    static Tenant unwrapTenant(Optional<Tenant> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Tenant.class);
    }
}
