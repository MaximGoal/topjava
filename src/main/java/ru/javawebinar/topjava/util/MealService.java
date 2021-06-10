package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    void add(LocalDateTime dateTime, String description, int calories);
    Meal read(int id);
    void update(int id, Meal meal);
    void delete(int id);
    List<Meal> getMealList();
    List<MealTo> getMealToList();
    int getLastId();
}
