package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(11, 0), LocalTime.of(21, 1), 2000);
        List<UserMealWithExcess> mealsTo = filteredByStreams(meals, LocalTime.of(11, 0), LocalTime.of(21, 1), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> map = new HashMap<>();

        // count total calories
        for (UserMeal meal : meals) {
            if (map.containsKey(meal.getDateTime().toLocalDate())) {
                map.put(meal.getDateTime().toLocalDate(), map.get(meal.getDateTime().toLocalDate()) + meal.getCalories());
            } else {
                map.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        }

        // collect to List<UserMealWithExcess>
        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal meal : meals) {
            boolean excess = map.get(meal.getDateTime().toLocalDate()) <= caloriesPerDay;
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        excess));
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        // count total calories
        Map<LocalDate, Integer> map =
                meals.stream()
                        .collect(Collectors.groupingBy
                                (UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        // collect to List<UserMealWithExcess>
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        (map.get(meal.getDateTime().toLocalDate()) <= caloriesPerDay)))
                .collect(Collectors.toList());
    }
}
