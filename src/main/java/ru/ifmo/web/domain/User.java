package ru.ifmo.web.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The {@code ru.ifmo.web.domain.User} entity class.
 * Objects of current class lie in database.
 *
 * @author Bobur Zakirov
 * @since 11/29/2020
 */
@Data
@Table(name = "Users")
@Entity
public class User implements Serializable {
      private static final long serialVersionUID = 2375287465863428524L;

      @GeneratedValue
      @Id
      private Long id;

      @Column(nullable = false, unique = true)
      private String username;
      private String password;

      public User() {
      }
      public User(String username, String password) {
            this.username = username;
            this.password = password;
      }
}
