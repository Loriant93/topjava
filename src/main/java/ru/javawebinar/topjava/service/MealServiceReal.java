package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MealServiceReal implements MealService {
    private static final Logger log = LoggerFactory.getLogger(MealServiceReal.class);
    private static Map<Integer, Meal> meals = new ConcurrentHashMap<>((Map.of(0,
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            1,
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            2,
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            3,
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            4,
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            5,
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            6,
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    )));

    @Override
    public void addMeal(Meal meal) {
        log.debug("addMeal" + meal.toString());

        meals.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(Integer id) {
        log.debug("deleteMeal" + id);

        meals.remove(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        log.debug("updateMeal" + meal.toString());

        meals.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        log.debug("getAllMeals");

        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getMealById(Integer id) {
        log.debug("getMealById" + id);

        return meals.get(id);
    }
}
