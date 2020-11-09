package ru.ifmo.web.controller;

import lombok.Getter;
import lombok.Setter;

import ru.ifmo.web.dao.Dao;
import ru.ifmo.web.dao.PointDao;
import ru.ifmo.web.model.Point;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code ru.ifmo.web.controller.ControllerBean} class controls
 * all action in the application. This <b>managed bean</b> build a new
 * object of {@code ru.ifmo.web.model.Point} class and call method of
 * {@code initialize()} in it, after initializing inserts it to database,
 * removes from it and ect.
 *
 * @author Crazy
 * @see java.io.Serializable
 * @since 1.0
 */
@ManagedBean(name = "Controller")
@SessionScoped
public class ControllerBean implements Serializable {
      private static final long serialVersionUID = 978938612874152435L;

      /**
       * The name of 'persistence unit'
       */
      private static final String PERSISTENCE_UNIT_NAME = "DEFAULT";

      /**
       * The <b>data access object</b> manages all actions with database
       */
      private static Dao<Point> dao;

      /**
       * Entity
       */
      @Getter
      @Setter
      private Point point;

      /**
       * List of points, which are in database
       */
      public List<Point> points;

      public List<Point> getPoints() {
            refresh();
            return points;
      }

      /**
       * Refreshes the
       */
      private void refresh() {
            points = new ArrayList<>(dao.getPoints());
            Collections.reverse(points);
      }

      /**
       * The method {@code initialize()} calls after building constructor
       * and initializes values of bean's fields.
       */
      @PostConstruct
      public void initialize() {
            dao = new PointDao(PERSISTENCE_UNIT_NAME);
            point = new Point();
            refresh();
      }

      /**
       * The following fields mean the status of checkboxes (checked or not).
       */
      @Getter
      @Setter
      private boolean r1, r2, r3, r4, r5;

      /**
       * The method {@code insertPoint()} adds a new point if it fields
       * fully initialized, else not.
       */
      public void insertPoint() {

            if (point.getX() != null && point.getY() != null && checkPoint(point.getX(), point.getY())) {
                  Point point  = new Point();

                  point.setX(this.point.getX());
                  point.setY(this.point.getY());

                  if (r1 || r2 || r3 || r4 || r5) {
                        if (r1) point.setR(1d);
                        if (r2) point.setR(2d);
                        if (r3) point.setR(3d);
                        if (r4) point.setR(4d);
                        if (r5) point.setR(5d);

                        point.initialize();

                        dao.insertPoint(point);
                        refresh();
                  }
            }
      }

      /**
       * Checks value of a Point
       *
       * @param x  coordinate of a point by axis x
       * @param y  coordinate of a point by axis y
       * @return  boolean
       */
      private boolean checkPoint(double x, double y) {
            return x >= -5d && x <= 5 && y >= -5 && y <= 3;
      }

      /**
       * Remove the Point from database by it's Identificator.
       *
       * @param id  the Identificator of an element
       */
      public void removePoint(long id) {
            dao.removePoint(id);
            refresh();
      }
}
