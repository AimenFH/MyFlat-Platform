package stud.fhcampuswien.ac.at.myflatservices.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stud.fhcampuswien.ac.at.myflatservices.entity.Property;
import stud.fhcampuswien.ac.at.myflatservices.service.base.PropertyService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/property")
public class PropertyController {

    PropertyService propertyService;

    @PostMapping
    public ResponseEntity<Property> getProperty(@Valid @RequestBody Property property) {
        return new ResponseEntity<>(propertyService.saveProperty(property), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id){
       return new ResponseEntity<>(propertyService.getProperty(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Property>> getAllProperties(){
        return new ResponseEntity<>(propertyService.getProperties(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Property> deleteProperty(@PathVariable Long id){
        propertyService.deleteProperty(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
