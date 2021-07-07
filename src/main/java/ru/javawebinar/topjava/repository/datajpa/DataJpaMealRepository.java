package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository crudUserRepository) {
        this.crudRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    @Modifying
    public Meal save(Meal meal, int userId) {

        if (meal.getId() != null) {
            User u = crudRepository.getById(meal.getId()).getUser();
            if (u != null && u.id() != userId) {
                return null;
            }
        }

        if (meal.getUser() == null) {
            User user = crudUserRepository.getById(userId);
            meal.setUser(user);
        }

        Meal saved = crudRepository.save(meal);
        if (meal.isNew()) {
            Integer id = saved.getId();
            meal.setId(id);
        }
        return saved;
    }

    @Override
    @Transactional
    @Modifying
    public boolean delete(int id, int userId) {
            return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }
}
