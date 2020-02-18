package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return getAll(null, null, null, null);
    }

    public List<MealTo> getAll(LocalDate date1, LocalDate date2, LocalTime time1, LocalTime time2) {
        log.info("getAll filtered");
        List<Meal> mealsByDate = service.getAll(SecurityUtil.authUserId(), date1, date2);
        return MealsUtil.filteredByStreams(mealsByDate, SecurityUtil.authUserCaloriesPerDay(),
                meal -> DateTimeUtil.isBetweenInclusive(meal.getTime(), time1, time2));
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        Meal userMeal = new Meal(SecurityUtil.authUserId(), meal.getDateTime(),
                meal.getDescription(), meal.getCalories());
        log.info("create {}", userMeal);
        return service.create(userMeal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        Meal userMeal = new Meal(SecurityUtil.authUserId(), meal.getId(), meal.getDateTime(),
                meal.getDescription(), meal.getCalories());
        log.info("update {} with id={}", userMeal, id);
        assureIdConsistent(userMeal, id);
        service.update(userMeal, SecurityUtil.authUserId());
    }

}