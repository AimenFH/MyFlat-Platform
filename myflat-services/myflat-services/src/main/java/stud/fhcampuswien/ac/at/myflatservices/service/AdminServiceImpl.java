package stud.fhcampuswien.ac.at.myflatservices.service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stud.fhcampuswien.ac.at.myflatservices.entity.Admin;
import stud.fhcampuswien.ac.at.myflatservices.exception.EntityNotFoundException;
import stud.fhcampuswien.ac.at.myflatservices.repository.AdminRepository;
import stud.fhcampuswien.ac.at.myflatservices.service.base.AdminService;

import java.util.Optional;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Admin getAdmin(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return unwrapAdmin(admin, id);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    static Admin unwrapAdmin(Optional<Admin> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Admin.class);
    }
}
