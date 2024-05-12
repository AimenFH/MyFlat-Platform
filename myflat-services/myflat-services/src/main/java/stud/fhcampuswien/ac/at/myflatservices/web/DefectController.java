package stud.fhcampuswien.ac.at.myflatservices.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stud.fhcampuswien.ac.at.myflatservices.entity.Defect;
import stud.fhcampuswien.ac.at.myflatservices.service.base.DefectService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/defect")
public class DefectController {

    DefectService defectService;

    @GetMapping("/all")
    public ResponseEntity<List<Defect>> getDefects() {
        return new ResponseEntity<>(defectService.getAllDefects(), HttpStatus.OK);
    }

    @GetMapping("/tenant/{tenantId}/apartment/{apartmentId}")
    public ResponseEntity<Defect> getDefect(@PathVariable Long tenantId, @PathVariable Long apartmentId) {
        return new ResponseEntity<>(defectService.getDefect(tenantId, apartmentId), HttpStatus.OK);
    }

    @PostMapping("/tenant/{tenantId}/apartment/{apartmentId}")
    public ResponseEntity<Defect> saveDefect(@Valid @RequestBody Defect defect, @PathVariable Long tenantId, @PathVariable Long apartmentId) {
        return new ResponseEntity<>(defectService.saveDefect(defect, tenantId, apartmentId), HttpStatus.CREATED);
    }

    @PutMapping("/tenant/{tenantId}/apartment/{apartmentId}")
    public ResponseEntity<Defect> updateDefect(@Valid @RequestBody Defect defect, @PathVariable Long tenantId, @PathVariable Long apartmentId) {
        return new ResponseEntity<>(defectService.updateDefect(defect.getDescription(), defect.getStatus(), tenantId, apartmentId), HttpStatus.OK);
    }

    @DeleteMapping("/tenant/{tenantId}/apartment/{apartmentId}")
    public ResponseEntity<Defect> deleteDefect(@PathVariable Long tenantId, @PathVariable Long apartmentId) {
        defectService.deleteDefect(tenantId, apartmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
