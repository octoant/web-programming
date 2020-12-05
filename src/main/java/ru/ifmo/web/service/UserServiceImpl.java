package ru.ifmo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.web.domain.User;
import ru.ifmo.web.repository.UserRepository;

/**
 * @author Bobur Zakirov
 * @since 12/3/2020 03:11:35
 */
@Service
public class UserServiceImpl implements UserService {
      private final UserRepository repository;
      private final PasswordEncoder encoder;

      @Autowired
      public UserServiceImpl(UserRepository repository) {
            this.repository = repository;
            this.encoder = new BCryptPasswordEncoder();
      }

      @Override
      public User findByUsername(String username) {
            return repository.findByUsername(username);
      }

      @Override
      public void addNewUser(User user) {
            repository.save(user.encode(encoder));
      }
}
