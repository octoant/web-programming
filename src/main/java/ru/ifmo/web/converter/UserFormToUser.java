package ru.ifmo.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ifmo.web.domain.User;
import ru.ifmo.web.domain.UserForm;

/**
 * @author Bobur Zakirov
 * @since 12/1/2020 00:22:13
 */
@Component
public class UserFormToUser implements Converter<UserForm, User> {

      /**
       * Converts type {@code UserForm} to {@code User}
       */
      @Override
      public User convert(UserForm userForm) {
            User user = new User();

            user.setUsername(userForm.getUsername());
            user.setPassword(userForm.getPassword());

            return user;
      }
}
