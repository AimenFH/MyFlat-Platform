package stud.fhcampuswien.ac.at.myflatservices.service.base;

import stud.fhcampuswien.ac.at.myflatservices.entity.Tenant;
import stud.fhcampuswien.ac.at.myflatservices.entity.TenantApartment;

import java.util.List;

public interface TenantService {
    Tenant getTenant(Long id);

    List<Tenant> getTenants();

    Tenant saveTenant(Tenant tenant);

    void deleteTenant(long id);

    List<TenantApartment> tenantApartments(long id);
}
