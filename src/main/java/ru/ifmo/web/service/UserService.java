package ru.ifmo.web.service;

import ru.ifmo.web.domain.User;

/**
 * @author Bobur Zakirov
 * @since 12/1/2020 01:13:58
 */
public interface UserService {

      /**
       * Searches user with current username
       *
       * @param username nick of the user
       * @return an object of {@code User}
       */
      User findByUsername(String username);

      /**
       * Adds new user to the database
       *
       * @param user an object of {@code User}
       */
      void addNewUser(User user);
}
