package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealMemoryRepository {
    private static List<Meal> mealList = new CopyOnWriteArrayList<>();
    private static final int maxCaloriesPerDay = 2000;

    static {
        mealList.add(new Meal(LocalDateTime.now(), "breakfast", 650));
        mealList.add(new Meal(LocalDateTime.now(), "dinner", 1400));
        mealList.add(new Meal(LocalDateTime.now(), "supper", 1000));
        mealList.add(new Meal(LocalDateTime.now(), "one more", 300));
        mealList.add(new Meal(LocalDateTime.of(2021, 06, 07, 13, 0), "one more else", 200));
        mealList.add(new Meal(LocalDateTime.of(2021, 06, 07, 15, 0), "one more else 2", 500));
    }

    public static List<Meal> getMealList() {
        return mealList;
    }

    public static List<MealTo> getMealToList(List<Meal> meals) {
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, maxCaloriesPerDay);
    }

    public static int getMaxCaloriesPerDay() {
        return maxCaloriesPerDay;
    }
}
