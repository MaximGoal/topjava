package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicInteger userId = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    private boolean isBelongToUser(int mealId, int userId) {
        Optional<Meal> optionalMeal = repository.values().stream()
                .filter(meal -> meal.getId().equals(mealId))
                .findFirst();
        return optionalMeal.filter(meal -> meal.getUserId() == userId).isPresent();
    }

    @Override
    public Meal save(Meal meal) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (!isBelongToUser(meal.getId(), userId.get())) return null;
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        if (!isBelongToUser(id, userId.get())) return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        if (!isBelongToUser(id, userId.get())) return null;
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId.get())
                .sorted((o1, o2) -> {
                    if (o1.getDateTime().isBefore(o2.getDateTime())) return -1;
                    if (o1.getDateTime().isAfter(o2.getDateTime())) return 1;
                    else return 0;
                })
                .collect(Collectors.toList());
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }
}

