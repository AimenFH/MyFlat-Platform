package stud.fhcampuswien.ac.at.myflatservices.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stud.fhcampuswien.ac.at.myflatservices.entity.Tenant;
import stud.fhcampuswien.ac.at.myflatservices.entity.TenantApartment;
import stud.fhcampuswien.ac.at.myflatservices.service.base.TenantService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tenant")
public class TenantController {

    TenantService tenantService;

    @PostMapping
    public ResponseEntity<Tenant> saveTenant(@Valid @RequestBody Tenant tenant) {
        return new ResponseEntity<>(tenantService.saveTenant(tenant), HttpStatus.CREATED);
    }

    @GetMapping("/login/{id}")
    public ResponseEntity<Tenant> findTenantById(@PathVariable long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable long id) {
        return new ResponseEntity<>(tenantService.getTenant(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tenant>> getTenants() {
        return new ResponseEntity<>(tenantService.getTenants(), HttpStatus.OK);
    }

    @GetMapping("/{apartments}")
    public ResponseEntity<List<TenantApartment>> getTenantApartments(@PathVariable long id) {
        return new ResponseEntity<>(tenantService.tenantApartments(id), HttpStatus.OK);
    }

//    @GetMapping("/{apartment}/{defect}")
//    public ResponseEntity<List<Apartment>> getTenantApartmentDefects(){
//        return new ResponseEntity<>(tenantService.getApartments(), HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tenant> deleteTenant(@PathVariable long id) {
        tenantService.deleteTenant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
