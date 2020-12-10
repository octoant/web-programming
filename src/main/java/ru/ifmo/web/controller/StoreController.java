package ru.ifmo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.web.converter.PointFormToPoint;
import ru.ifmo.web.domain.Point;
import ru.ifmo.web.domain.PointForm;
import ru.ifmo.web.service.PointService;
import ru.ifmo.web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Bobur Zakirov
 * @since 12/7/2020 21:18:01
 */
@CrossOrigin
@RestController
@RequestMapping("/points")
public class StoreController {
      private final PointService pointService;
      private final UserService userService;

      @Autowired
      public StoreController(PointService pointService, UserService userService) {
            this.pointService = pointService;
            this.userService = userService;
      }

      /**
       * A converter {PointForm -> Point}
       */
      private PointFormToPoint pointFormToPoint;
      @Autowired
      public void setUserFormToUser(PointFormToPoint pointFormToPoint) {
            this.pointFormToPoint = pointFormToPoint;
      }

      /**
       * Gets a list of the points, which are added by current user
       */
      @GetMapping("/get-all")
      public List<Point> getAllPointsByUsername(Principal user) {
            return pointService.getPointByUsername(userService.findByUsername(user.getName()));
      }

      /**
       * Adds a new point to the table
       */
      @PostMapping("/add")
      public Point addNewPoint(@RequestBody @Valid PointForm pointForm, Principal user) {
            Point point = pointFormToPoint.convert(pointForm);

            assert point != null;
            point.setUzer(userService.findByUsername(user.getName()));
            pointService.addNewPoint(point.initialize()); // new point
            return point;
      }

      /**
       * DDeletes all points, which are added bu current user
       */
      @DeleteMapping("/delete-all")
      public void deleteAllByUsername(Principal user) {
            pointService.deleteAllPointsByUsername(userService.findByUsername(user.getName()));
      }
}
