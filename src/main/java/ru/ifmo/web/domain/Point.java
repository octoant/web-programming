package ru.ifmo.web.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The {@code ru.ifmo.web.domain.Point} entity class.
 * Objects of current class lie in database.
 *
 * @author Bobur Zakirov
 * @since 11/29/2020
 */
@Data
@Table(name = "Points")
@Entity
public class Point implements Serializable {
      private static final long serialVersionUID = 6783264645862385654L;

      @GeneratedValue
      @Id
      private Long ID;

      // coordinates and radius of a point
      private Double x, y, r;

      // no comments =)
      private Boolean status;
      private String history, duration;

      // the user who added this Point
      @ManyToOne
      private User uzer;

      public Point() {
      }
      public Point(Double x, Double y, Double r) {
            this.x = x;
            this.y = y;
            this.r = r;
      }
}
