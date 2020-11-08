package ru.ifmo.web.dao;

import java.util.List;

/**
 * The user of {@code ru.ifmo.web.dao.Dao} interface can have
 * access to <i>database</i> and control with it <i>elements</i>
 * (DAO - Data Access Object).
 *
 * @param <T>  the type of elements in this dao
 *
 * @author Crazy
 * @since 1.0
 */
public interface Dao<T> {

      /**
       * Inserts new element to the database.
       *
       * @param type  the type of elements
       */
      void insertPoint(T type);

      /**
       * Removes the element from the database by ID.
       *
       * @param id  the Identificator of an element
       */
      void removePoint(Long id);

      /**
       * Returns list of all elements, which are in database.
       *
       * @return  java.util.List
       */
      List<T> getPoints();
}
