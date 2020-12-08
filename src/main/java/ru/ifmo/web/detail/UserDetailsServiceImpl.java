package ru.ifmo.web.detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ifmo.web.domain.User;
import ru.ifmo.web.service.UserService;

/**
 * The class requires only one read-only method, which simplifies
 * support for new data-access strategies.
 *
 * @author Bobur Zakirov
 * @since 12/8/2020 02:51:03
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
      private final UserService userService;

      @Autowired
      public UserDetailsServiceImpl(UserService userService) {
            this.userService = userService;
      }

      /**
       * Locates the user based on the username. In the actual implementation, the
       * search may possibly be case insensitive, or case insensitive depending on
       * how the implementation instance is configured. In this case, the UserDetails
       * object that comes back may have a username that is of a different case
       * than what was actually requested..
       *
       * @param username the username identifying the user whose data is required.
       * @return a fully populated user record (never null)
       * @throws UsernameNotFoundException - if the user could not be found or the
       *          user has no GrantedAuthority
       */
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userService.findByUsername(username);
            if (user == null) throw new UsernameNotFoundException("User not found!");
            return new UserDetailsImpl(user);
      }
}
