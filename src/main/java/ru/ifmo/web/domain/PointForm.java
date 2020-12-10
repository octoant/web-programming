package ru.ifmo.web.domain;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Bobur Zakirov
 * @since 11/30/2020 23:57:02
 */
@Data
public class PointForm {

      @NotNull @Min(-4) @Max(4)
      private Double x;

      @NotNull @Min(-5) @Max(3)
      private Double y;

      @NotNull @Min(-4) @Max(4)
      private Double r;
}
