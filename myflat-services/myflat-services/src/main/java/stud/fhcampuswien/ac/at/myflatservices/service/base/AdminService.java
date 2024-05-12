package stud.fhcampuswien.ac.at.myflatservices.service.base;

import stud.fhcampuswien.ac.at.myflatservices.entity.Admin;

public interface AdminService {

    Admin getAdmin(Long id);
    Admin saveAdmin(Admin admin);


}
