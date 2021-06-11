package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMeal {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final AtomicInteger id = new AtomicInteger(0);
    private static AtomicInteger lastId = new AtomicInteger(0);

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        id.set(lastId.incrementAndGet());
    }

    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id.set(id);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getId() {
        return id.get();
    }



    @Override
    public String toString() {
        return "Meal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
