package fhcampus.myflat.services.user;

import fhcampus.myflat.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class for user-related operations.
 * This class provides functionality to retrieve the currently authenticated user.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return User The currently authenticated user.
     */
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}