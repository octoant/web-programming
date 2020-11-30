package ru.ifmo.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ifmo.web.domain.User;
import ru.ifmo.web.domain.UserForm;

/**
 * @author Bobur Zakirov
 * @since 12/1/2020 00:21:59
 */
@Component
public class UserToUserForm implements Converter<User, UserForm> {

      /**
       * Converts type {@code User} to {@code UserForm}
       */
      @Override
      public UserForm convert(User user) {
            UserForm userForm = new UserForm();

            userForm.setUsername(user.getUsername());
            userForm.setPassword(user.getPassword());

            return userForm;
      }
}
