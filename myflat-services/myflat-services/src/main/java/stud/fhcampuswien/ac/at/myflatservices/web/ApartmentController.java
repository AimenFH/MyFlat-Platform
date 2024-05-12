package stud.fhcampuswien.ac.at.myflatservices.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stud.fhcampuswien.ac.at.myflatservices.service.base.ApartmentService;

@AllArgsConstructor
@RestController
@RequestMapping("/apartment")
public class ApartmentController {

    ApartmentService apartmentService;


}
