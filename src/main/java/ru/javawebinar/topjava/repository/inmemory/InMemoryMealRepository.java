package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

//    надо ли это поле "Map<Integer, List<Meal>> userMealRepository" - вопрос
//    private final Map<Integer, List<Meal>> userMealRepository;
//    private final AtomicInteger userId = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
        MealsUtil.meals.forEach(meal -> meal.setUserId(SecurityUtil.authUserId()));

//        userMealRepository = MealsUtil.meals.stream()
//                .collect(Collectors.groupingBy(Meal::getUserId));
    }

//    private boolean isBelongToUser(int mealId, int userId) {
//        Optional<Meal> optionalMeal = repository.values().stream()
//                .filter(meal -> meal.getId().equals(mealId))
//                .findFirst();
//        return optionalMeal.filter(meal -> meal.getUserId() == userId).isPresent();
//    }

    @Override
    public Meal save(Meal meal) {

//        List<Meal> userMealList = userMealRepository.get(meal.getUserId());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
//            userMealList.add(meal);
            return meal;
        }

//        if (!isBelongToUser(meal.getId(), userId.get())) return null;

        // handle case: update, but not present in storage
        Meal present = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

//        if (userMealList.get(meal.getId()) != null) {
//            userMealList.remove(meal);
//        }
//        userMealList.add(present);
        return present;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }


    @Override
    public Collection<Meal> getAll() {
        return repository.values()
                .stream()
                .sorted((o1, o2) -> {
                    if (o1.getDateTime().isBefore(o2.getDateTime())) return -1;
                    if (o1.getDateTime().isAfter(o2.getDateTime())) return 1;
                    else return 0;
                })
                .collect(Collectors.toList());
    }

//    public Collection<Meal> getAllUserMeals(int userId) {
//        return userMealRepository.get(userId)
//                .stream()
//                .sorted((o1, o2) -> {
//                    if (o1.getDateTime().isBefore(o2.getDateTime())) return -1;
//                    if (o1.getDateTime().isAfter(o2.getDateTime())) return 1;
//                    else return 0;
//                })
//                .collect(Collectors.toList());
//    }

//    public void setUserId(int userId) {
//        this.userId.set(userId);
//    }
}

