package FHCampus.MyFlat.controllers;

import FHCampus.MyFlat.dtos.*;
import FHCampus.MyFlat.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


   @GetMapping("/v1/{userId}")
   public ResponseEntity<UserDto> getTenantById(@PathVariable long userId) {
       UserDto userDto = customerService.getTenantById(userId);
       if(userDto != null) return  ResponseEntity.ok(userDto);
       return ResponseEntity.notFound().build();
   }


    @GetMapping("/v1/apartment/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }



    @PostMapping("/v1/defect/{defectId}")
    public ResponseEntity<?> reportDefect(@PathVariable Long defectId, @RequestBody DefectDto defectDto) {
        DefectReport defectreport = customerService.defectReport(defectId, defectDto);
        if (defectreport.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defectreport.getMessage());
    }
}

