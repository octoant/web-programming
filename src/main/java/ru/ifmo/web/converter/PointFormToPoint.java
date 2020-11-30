package ru.ifmo.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ifmo.web.domain.Point;
import ru.ifmo.web.domain.PointForm;

/**
 * @author Bobur Zakirov
 * @since 12/1/2020 00:21:41
 */
@Component
public class PointFormToPoint implements Converter<PointForm, Point> {

      /**
       * Converts type {@code PointForm} to {@code Point}
       */
      @Override
      public Point convert(PointForm pointForm) {
            Point point = new Point();

            point.setX(pointForm.getX());
            point.setY(pointForm.getY());
            point.setR(pointForm.getR());

            return point;
      }
}
