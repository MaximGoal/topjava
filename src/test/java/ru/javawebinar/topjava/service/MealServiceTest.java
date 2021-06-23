package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    int userId = 100000;
    static DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal expected = new Meal(100002,
                                    LocalDateTime.parse("11.11.2001 11:00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                                    "first meal",
                                    1000);
        Meal actual = service.get(100002, userId);
        System.out.println(expected);
        System.out.println(actual);
        assertEquals(actual, expected);
    }

    @Test
    public void delete() {
        service.delete(100002, userId);
        assertThrows(NotFoundException.class, () -> service.get(100002, userId));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = getAllMealsFromUtil();
        List<Meal> isBetween = getAllMealsFromUtil().stream()
                .filter(meal -> Util.isBetweenHalfOpen(
                                                        meal.getDateTime(),
                                                        meals.get(4).getDateTime(),
                                                        meals.get(5).getDateTime().plusMinutes(5)))
                .collect(Collectors.toList());
        assertMatch(isBetween, meals.get(4), meals.get(5));
    }

    @Test
    public void getAll() {
        List<Meal> actual = service.getAll(userId);
        List<Meal> expected = getAllMealsFromUtil();
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, userId);
        assertMatch(service.get(100003, userId), updated);
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), userId);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        LocalDateTime duplicateDateTime = service.get(100002, userId).getDateTime();
        Meal newMeal = new Meal(null,
                duplicateDateTime,
                "first meal",
                1000);
        System.out.println(newMeal);
        assertThrows(DuplicateKeyException.class, () ->
                service.create(newMeal, userId));
    }

    @Test
    public void notFoundUserIDException() {
        assertThrows(NotFoundException.class, () -> service.get(100003, 110000));
    }
}