package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_ID = 100002;
    public static final int NOT_FOUND = 10;
    static DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static final Meal meal1 = new Meal(100002, LocalDateTime.parse("11.11.2001 11:00:00", pattern), "first meal", 1000);
    private static final Meal meal2 = new Meal(100003, LocalDateTime.parse("11.11.2001 15:00:00", pattern), "second meal", 1500);
    private static final Meal meal3 = new Meal(100004, LocalDateTime.parse("14.11.2001 10:00:00", pattern), "first meal", 500);
    private static final Meal meal4 = new Meal(100005, LocalDateTime.parse("14.11.2001 14:00:00", pattern), "second meal", 1000);
    private static final Meal meal5 = new Meal(100006, LocalDateTime.parse("19.11.2001 09:00:00", pattern), "first meal", 1000);
    private static final Meal meal6 = new Meal(100007, LocalDateTime.parse("19.11.2001 14:00:00", pattern), "second meal", 999);

    public static Meal getMeal1() {
        System.out.println(meal1);
        return meal1;
    }

    public static Meal getUpdated() {
        Meal meal = new Meal();
        meal.setId(100003);
        meal.setDateTime(LocalDateTime.now());
        meal.setDescription("Updated");
        meal.setCalories(1111);
        return meal;
    }

    public static Meal getNew() {
        return new Meal(null,  LocalDateTime.parse("21.06.2021 09:00:01", pattern), "New meal", 1555);
    }

    public static List<Meal> getAllMealsFromUtil() {
        return Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, Meal... meals) {
        assertThat(actual).isEqualTo(Arrays.asList(meals));
    }
}
