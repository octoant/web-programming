package ru.ifmo.web.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.web.domain.Point;
import ru.ifmo.web.domain.User;

import java.util.List;

/**
 * @author Bobur Zakirov
 * @since 11/29/2020
 */
public interface PointRepository extends CrudRepository<Point, Long> {

      /**
       * Gets a list of the points, which are added by current user
       *
       * @param user an object of {@code User}
       * @return list of the points
       */
      List<Point> getAllByUzer(User user);

      /**
       * Deletes all points, which are added by current user
       *
       * @param user an object of {@code User}
       */
      void deleteAllByUzer(User user);
}
