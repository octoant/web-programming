package ru.ifmo.web.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.web.domain.Point;
import ru.ifmo.web.domain.User;

import java.util.List;

/**
 * @author Bobur Zakirov
 * @since 12/1/2020 01:13:41
 */
@Transactional
public interface PointService {

      /**
       * Gets list of all points, which are added by current user
       *
       * @param user an object of {@code User}
       * @return list of the points
       */
      List<Point> getPointByUsername(User user);

      /**
       * Adds a new point to the database
       *
       * @param point an object of {@code Point}
       */
      void addNewPoint(Point point);

      /**
       * Deletes all points, which are added bu current user
       *
       * @param user an object of {@code User}
       */
      void deleteAllPointsByUsername(User user);
}
