package ru.ifmo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.web.domain.Point;
import ru.ifmo.web.domain.User;
import ru.ifmo.web.repository.PointRepository;

import java.util.List;

/**
 * @author Bobur Zakirov
 * @since 12/2/2020 18:05:52
 */
@Service
public class PointServiceImpl implements PointService {
      private final PointRepository pointRepository;

      @Autowired
      public PointServiceImpl(PointRepository pointRepository) {
            this.pointRepository = pointRepository;
      }

      @Override
      public List<Point> getPointByUsername(User user) {
            return pointRepository.getAllByUzer(user);
      }

      @Override
      public void addNewPoint(Point point) {
            pointRepository.save(point);
      }

      @Override
      public void deleteAllPointsByUsername(User user) {
            pointRepository.deleteAllByUzer(user);
      }
}
