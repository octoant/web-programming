package ru.ifmo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
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
      private final UserRepository userRepository;
      private final PasswordEncoder passwordEncoder;

      @Autowired
      public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
      }

      @Override
      public User findByUsername(String username) {
            return userRepository.findByUsername(username);
      }

      @Override
      public void addNewUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
      }
}
