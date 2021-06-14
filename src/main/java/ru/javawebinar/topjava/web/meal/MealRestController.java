package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;



// TODO: 4.4. конвертацию в MealTo можно делать как в слое web, так и в service
// TODO: transfer all userId checking into this Controller class

public class MealRestController {

    private int userId = SecurityUtil.authUserId();
    private MealService service = new MealService();
    private static final Logger log = getLogger(MealRestController.class);


    public Collection<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public MealTo get(int id) {
        log.info("get {}", id);
//        return service.get(id, userId);
        return MealsUtil.filterByPredicate(service.getAll(userId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                meal -> meal.getId() == id)
                .get(0);
    }

    public MealTo create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(userId);
        service.create(meal);
        boolean exceed = MealsUtil.filterByPredicate(service.getAll(userId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                o -> o.getUserId() == meal.getUserId())
                .stream()
                .filter(mealTo -> mealTo.getId() == meal.getId())
                .findFirst().get().isExcess();
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
//        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}