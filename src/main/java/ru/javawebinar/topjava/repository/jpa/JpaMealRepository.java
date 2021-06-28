package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    UserRepository jpaUserRepository;

    public JpaMealRepository(@Qualifier("jpaUserRepository") UserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(jpaUserRepository.get(userId));
        try {
            if (meal.isNew()) {
                em.persist(meal);
                return meal;
            } else if (meal.getUser().getId() == userId)
                return em.merge(meal);
            else return null;
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal;
        try {
            meal = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.IS_BETWEEN, Meal.class)
                .setParameter("startTime", startDateTime)
                .setParameter("endTime", endDateTime)
                .setParameter("user_id", userId)
                .getResultList();
    }
}