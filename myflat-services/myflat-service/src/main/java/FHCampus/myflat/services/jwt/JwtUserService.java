package fhcampus.myflat.services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtUserService {

    UserDetailsService userDetailsService();
}
