package ru.ifmo.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ifmo.web.domain.Point;
import ru.ifmo.web.domain.PointForm;

/**
 * @author Bobur Zakirov
 * @since 12/1/2020 00:21:23
 */
@Component
public class PointToPointForm implements Converter<Point, PointForm> {

      /**
       * Converts type {@code Point} to {@code PointForm}
       */
      @Override
      public PointForm convert(Point point) {
            PointForm pointForm = new PointForm();

            pointForm.setX(point.getX());
            pointForm.setY(point.getY());
            pointForm.setR(point.getR());

            return pointForm;
      }
}
