package stud.fhcampuswien.ac.at.myflatservices.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stud.fhcampuswien.ac.at.myflatservices.entity.Admin;
import stud.fhcampuswien.ac.at.myflatservices.service.base.AdminService;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/login/{id}")
    public ResponseEntity<Admin> findAdminByID(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
