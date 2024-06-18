package fhcampus.myflat.controllers;

import fhcampus.myflat.dtos.DefectDto;
import fhcampus.myflat.dtos.DefectReport;
import fhcampus.myflat.dtos.UserDto;
import fhcampus.myflat.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/tenant")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/v1/{userId}")
    public ResponseEntity<UserDto> getTenantById(@PathVariable long userId) {
        UserDto userDto = customerService.getTenantById(userId);
        if (userDto != null) return ResponseEntity.ok(userDto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/v1/apartment/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }

    @PostMapping("/v1/defect")
    public ResponseEntity<?> reportDefect(@RequestBody DefectDto defectDto) throws IOException {
        DefectReport defectreport = customerService.defectReport(defectDto);
        if (defectreport.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defectreport.getMessage());
    }
}

