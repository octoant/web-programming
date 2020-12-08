package ru.ifmo.web.detail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ifmo.web.domain.User;

import java.util.Collection;
import java.util.Collections;

/**
 * Provides core user information.
 *
 * @author Bobur Zakirov
 * @since 12/8/2020 02:42:28
 */
public class UserDetailsImpl implements UserDetails {
      private final User user;

      public UserDetailsImpl(User user) {
            this.user = user;
      }

      /**
       * Returns the authorities granted to the user. Cannot return null.
       *
       * @return the authorities, sorted by natural key (never null)
       */
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority("USER"));
      }

      /**
       * Returns the username used to authenticate the user. Cannot return null.
       *
       * @return the username (never null)
       */
      @Override
      public String getUsername() {
            return user.getUsername();
      }

      /**
       * Returns the password used to authenticate the user.
       *
       * @return the password
       */
      @Override
      public String getPassword() {
            return user.getPassword();
      }

      /**
       * Indicates whether the user's account has expired. An expired account cannot
       * be authenticated.
       *
       * @return true if the user's account is valid (ie non-expired), false if no
       *          longer valid (ie expired)
       */
      @Override
      public boolean isAccountNonExpired() {
            return false;
      }

      /**
       * Indicates whether the user is locked or unlocked. A locked user cannot be
       * authenticated.
       *
       * @return true if the user is not locked, false otherwise
       */
      @Override
      public boolean isAccountNonLocked() {
            return false;
      }

      /**
       * Indicates whether the user's credentials (password) has expired. Expired
       * credentials prevent authentication.
       *
       * @return true if the user's credentials are valid (ie non-expired), false
       *          if no longer valid (ie expired)
       */
      @Override
      public boolean isCredentialsNonExpired() {
            return false;
      }

      /**
       * Indicates whether the user is enabled or disabled. A disabled user cannot
       * be authenticated.
       *
       * @return true if the user is enabled, false otherwise
       */
      @Override
      public boolean isEnabled() {
            return false;
      }
}
