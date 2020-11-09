package ru.ifmo.web.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code ru.ifmo.web.model.Point} entity class.
 * Objects of current class lie in database.
 *
 * @author Crazy
 * @see java.io.Serializable
 * @since 1.0
 */
@Entity
@Table(name = "Point")
@Data
public class Point implements Serializable {
      private static final long serialVersionUID = 6783264645862385654L;

      @GeneratedValue(strategy = GenerationType.SEQUENCE)
      @Id
      private long id;

      // Coordinates and radius of point
      private Double x, y, r;

      // no comments =)
      private Boolean status;
      private String time, duration;

      /**
       * The method initializes unsetted fields of the point
       * ({@code status, time, duration}).
       */
      public void initialize() {

            // default time pattern for showing time of the executing
            time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());

            // checking status of the point
            long startTime = System.nanoTime();
            status = checkArea(x, y, r);
            long endTime = System.nanoTime();

            long diffTime = endTime - startTime;
            duration = String.format("%.6f", (double) diffTime / 10_000_000); // ms

            this.y = ((int) (y * 1000000)) / 1000000d;
      }

      /**
       * Checks the status of point (his os not)
       *
       * @param x  position of point in axe 'x'
       * @param y  position of point in axe 'y'
       * @param r  radius (R)
       * @return   boolean value
       */
      private boolean checkArea(double x, double y, double r) {
            if (x >= 0 && y <= 0 && y >= x / 2 - r / 2) {
                  return true;
            }
            if (x <= 0 && y >= 0 && x >= - r / 2 && y <= r) {
                  return true;
            }
            if (x <= 0 && y <= 0 && x * x + y * y <= r * r) {
                  return true;
            }
            return false;
      }
}
