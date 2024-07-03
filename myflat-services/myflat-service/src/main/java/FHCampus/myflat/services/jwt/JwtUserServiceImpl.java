package fhcampus.myflat.services.jwt;

import fhcampus.myflat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for JWT user details management.
 * This class implements the JwtUserService interface to provide user details loading functionality
 * based on the username (email in this context).
 */
@Service
@RequiredArgsConstructor
public class JwtUserServiceImpl implements JwtUserService {

    private final UserRepository userRepository;

    /**
     * Loads the user details by username (email) and returns a UserDetailsService instance.
     * If the user is not found in the repository, it throws a UsernameNotFoundException.
     *
     * @return UserDetailsService A UserDetailsService instance for the found user.
     * @throws UsernameNotFoundException if the user cannot be found in the repository.
     */
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findFirstByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}