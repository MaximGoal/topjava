package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

// TODO: make only one request to repository (return repo.etc())

@Service
public class MealService {

    @Autowired
    private MealRepository repository = new InMemoryMealRepository();

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        if(repository.get(id).getUserId() != userId) throw new NotFoundException("No valid user id " + id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id, int userId) {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        if (meal.getUserId() != userId) throw new NotFoundException("No valid user id " + id);
        return meal;
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll().
                stream().filter(meal -> meal.getUserId()==userId)
                .collect(Collectors.toList());
    }

    public void update(Meal meal, int userId) {
        if (meal.getUserId() != userId) throw new NotFoundException("No valid user id ");
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

}