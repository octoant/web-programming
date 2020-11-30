package ru.ifmo.web.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.web.domain.User;

/**
 * @author Bobur Zakirov
 * @since 11/29/2020
 */
public interface UserRepository extends CrudRepository<User, Long> {

      /**
       * Finds the user by its username
       *
       * @param username the nick of the user
       * @return an object of {@code User}
       */
      User findByUsername(String username);
}
