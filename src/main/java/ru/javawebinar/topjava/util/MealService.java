package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MealService {
    private final List<Meal> mealList;

    public MealService() {
        mealList  = MealMemoryRepository.getMealList();
    }

    public void add (LocalDateTime dateTime, String description, int calories) {
        mealList.add(new Meal(dateTime, description, calories));
    }

    public Meal read (String description) {
        return mealList.stream()
                .filter(m -> m.getDescription().equals(description))
                .findFirst().orElse(null);
    }

    public void delete (Meal meal) {
        mealList.remove(meal);
    }

    public void update (Meal meal) {
        Optional<Meal> removed = mealList.stream()
                .filter(m -> m.getDateTime().equals(meal.getDateTime()))
                .findFirst();

        if (removed.isPresent()) {
            mealList.remove(removed.get());
            mealList.add(meal);
        }
    }
}
