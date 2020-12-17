package ru.ifmo.web.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

      // initializes value of fields
      public Point initialize() {
            history = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
            long startTime = System.nanoTime();
            status = x>=-r&&x<=0&&y>=0&&y<=r || x>=0&&y>=0&&r>=2*y&&x*x+y*y<=r*r/4 || x<=0&&y<=0&&x+2*y>=-r;
            long endTime = System.nanoTime();
            duration = String.format("%.6f", (double) (endTime - startTime) / 10_000_000);
            return this;
      }
}
