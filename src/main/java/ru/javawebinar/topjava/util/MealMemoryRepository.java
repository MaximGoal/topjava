package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealMemoryRepository {
    private static List<Meal> mealList = new ArrayList<>();

    static {
        mealList.add(new Meal(LocalDateTime.now(), "breakfast", 650));
        mealList.add(new Meal(LocalDateTime.now(), "dinner", 1400));
        mealList.add(new Meal(LocalDateTime.now(), "supper", 1000));
    }

    public static List<Meal> getMealList() {
        return mealList;
    }
}
