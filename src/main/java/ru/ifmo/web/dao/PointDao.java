package ru.ifmo.web.dao;

import ru.ifmo.web.model.Point;

import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * The {@code ru.ifmo.web.dao.PointDao} class implements
 * {@code ru.ifmo.web.dao.Dao} and {@code java.io.Serializable}
 * interfaces. It's the concrete class that works with
 * {@code ru.ifmo.web.model.Point} class and provides actions with it.
 *
 * @author Crazy
 * @see java.io.Serializable
 * @see ru.ifmo.web.dao.Dao
 * @since 1.0
 */
@SessionScoped
public class PointDao implements Dao<Point>, Serializable {
      private static final long serialVersionUID = 1234254125366666663L;

      /**
       * The entity manager factory created entity managers
       */
      @PersistenceUnit
      private final EntityManagerFactory entityManagerFactory;

      /**
       * The entity manager provides database accesses.
       */
      @PersistenceContext
      private EntityManager entityManager;

      /**
       * The constructor.
       *
       * @param persistenceUnitName  the name of the persistence unit
       */
      public PointDao(String persistenceUnitName) {
            this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
      }

      /**
       *
       * @return  javax.persistence.EntityManager 
       */
      private EntityManager getEntityManager() {
            if (entityManager == null || !entityManagerFactory.isOpen()) {
                  entityManager = entityManagerFactory.createEntityManager();
            }
            return entityManager;
      }

      /**
       * Query for getting all points from database
       */
      private static final String REMOVE$BY$ID = "select p from Point p  where p.id=:id";
      
      /**
       * Query for removing point, which id equal to this
       */
      private static final String GET$ALL = "select p from Point p";


      /**
       * Inserts a new Point to database.
       *
       * @param point  the type of the entity
       */
      @Override
      public void insertPoint(Point point) {
            EntityManager entityManager = this.getEntityManager();
            try {
                  entityManager.getTransaction().begin();
                  entityManager.persist(point);
                  entityManager.getTransaction().commit();
            } catch (RuntimeException exception) {
                  entityManager.getTransaction().rollback();
                  throw exception;
            }
      }

      /**
       * Remove the Point from database by it's Identificator.
       *
       * @param id  the Identificator of point
       */
      @Override
      public void removePoint(Long id) {
            EntityManager entityManager = this.getEntityManager();
            try {
                  entityManager.getTransaction().begin();

                  Query query = entityManager.createQuery(REMOVE$BY$ID);
                  query.setParameter("id", id);
                  Point point = (Point) query.getSingleResult();

                  entityManager.remove(point);
                  entityManager.getTransaction().commit();
            } catch (RuntimeException exception) {
                  entityManager.getTransaction().rollback();
                  throw exception;
            }
      }

      /**
       * Returns list of the Points.
       *
       * @return  java.util.List
       */
      @Override
      public List<Point> getPoints() {
            Query query = getEntityManager().createQuery(GET$ALL);
            return query.getResultList();
      }
}
