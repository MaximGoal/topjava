package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MealCollector
        implements Collector<UserMeal, ArrayList<UserMeal>, List<UserMealWithExcess>> {

    Map<LocalDate, Integer> map;
    int maxCalories;
    LocalTime startTime;
    LocalTime endTime;

    public MealCollector(int maxCalories, LocalTime startTime, LocalTime endTime) {
        this.map = new HashMap<>();
        this.maxCalories = maxCalories;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Supplier<ArrayList<UserMeal>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<ArrayList<UserMeal>, UserMeal> accumulator() {
        return (list, meal) -> {
            if (map.containsKey(meal.getDateTime().toLocalDate())) {
                map.put(meal.getDateTime().toLocalDate(), map.get(meal.getDateTime().toLocalDate()) + meal.getCalories());
            } else {
                map.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }

            list.add(meal);
        };
    }

    @Override
    public BinaryOperator<ArrayList<UserMeal>> combiner() {
        return (l, r) -> {
            l.addAll(r);
            return l;
        };
    }

    @Override
    public Function<ArrayList<UserMeal>, List<UserMealWithExcess>> finisher() {
        return list -> list.stream()
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                       meal.getDescription(),
                       meal.getCalories(),
                       map.get(meal.getDateTime().toLocalDate()) <= maxCalories)
                )
                .filter(p -> TimeUtil.isBetweenHalfOpen(p.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
