package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealService {

    private MealRepository repository = new InMemoryMealRepository();

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        if(repository.get(id).getUserId() != userId) return;

        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id, int userId) {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        if (meal.getUserId() != userId) return null;
        return meal;
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll().
                stream().filter(meal -> meal.getUserId()==userId)
                .collect(Collectors.toList());
    }

    public void update(Meal meal, int userId) {
        if (meal.getUserId() != userId) return;
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

}