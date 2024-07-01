package fhcampus.myflat.servicesTest.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtUserService {

    UserDetailsService userDetailsService();
}
