package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemService implements MealService {
    private final List<Meal> mealList;
    private final List<MealTo> mealToList;

    public MealMemService() {
        mealList  = MealMemoryRepository.getMealList();
        mealToList = MealMemoryRepository.getMealToList(mealList);
    }

    public void add (LocalDateTime dateTime, String description, int calories) {
        Meal item = new Meal(dateTime, description, calories);
        mealList.add(item);
        mealToList.add(new MealTo(
                item.getDateTime(),
                item.getDescription(),
                item.getCalories(),
                new AtomicInteger(item.getId()),
                MealsUtil.caloriesPerDayMap(mealList).get(item.getDate()) > MealMemoryRepository.getMaxCaloriesPerDay()));
    }

    public Meal read (int id) {
        return mealList.get(id);
    }

    public void delete (int id) {
        mealList.removeIf(meal -> meal.getId() == id);
        mealToList.removeIf(meal -> meal.getId() == id);
    }

    public void update (int id, Meal meal) {
        Optional<Meal> removed = mealList.stream()
                .filter(m -> m.getDateTime().equals(mealList.get(id).getDateTime()))
                .findFirst();

        if (removed.isPresent()) {
            mealList.remove(removed.get());
            mealList.add(meal);
        }
    }

    @Override
    public List<Meal> getMealList() {
        return mealList;
    }

    @Override
    public List<MealTo> getMealToList() {
        return mealToList;
    }

    public int getLastId() {
        return mealToList.get(mealToList.size()-1).getId();
    }
}
