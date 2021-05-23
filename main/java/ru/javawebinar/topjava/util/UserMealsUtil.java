package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        // count total calories
        int totalCaloriesPerDay = 0;
        boolean excess = totalCaloriesPerDay > caloriesPerDay;
        TimeUtil timeUtil = new TimeUtil();
        for (UserMeal meal : meals) {
            while (!excess) {
                if (timeUtil.isBetweenHalfOpen(meal.getDateTime(), startTime, endTime)) {
                    totalCaloriesPerDay += meal.getCalories();
                    excess = totalCaloriesPerDay > caloriesPerDay;
                }
            }
        }

        // collect to List<UserMealWithExcess>
        List<UserMealWithExcess> result = new ArrayList();
        for (UserMeal meal : meals) {
            if (timeUtil.isBetweenHalfOpen(meal.getDateTime(), startTime, endTime)) {
                result.add(new UserMealWithExcess(  meal.getDateTime(),
                                                    meal.getDescription(),
                                                    meal.getCalories(),
                                                    excess))
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        // count total calories
        int totalCaloriesPerDay = 0;
        boolean excess = false;
        TimeUtil timeUtil = new TimeUtil();

        meals.stream()
                .filter(timeUtil.isBetweenHalfOpen(meal.getDateTime(), startTime, endTime))
                .forEach(meal -> totalCaloriesPerDay += meal.getCalories())
                .collect(Collectors.toList());

        // collect to List<UserMealWithExcess>
        TimeUtil timeUtil = new TimeUtil();
        List<UserMealWithExcess> result =
                meals.stream()
                .filter(timeUtil.isBetweenHalfOpen(meal.getDateTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                                                    meal.getDescription(),
                                                    meal.getCalories(),
                                                    excess))
                .collect(Collectors.toList());

        return result;
    }
}
