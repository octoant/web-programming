package ru.ifmo.web.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Bobur Zakirov
 * @since 11/30/2020 23:57:12
 */
@Data
public class UserForm {

      @NotEmpty
      private String username;

      @NotEmpty
      private String password;
}
