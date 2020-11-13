package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {
    public static final int FIRST_MEAL_ID = AbstractBaseEntity.START_SEQ + 2;
    public static final int SECOND_MEAL_ID = AbstractBaseEntity.START_SEQ + 3;

    public static final Meal firstMeal = new Meal(100002,
            LocalDateTime.of(2020, Month.OCTOBER, 18, 10, 23, 54), "breakfast", 200);
    public static final Meal secondMeal = new Meal(100003,
            LocalDateTime.of(2020, Month.OCTOBER, 19, 15, 23, 54), "lunch", 1000);
    public static final Meal thirdMeal = new Meal(100004,
            LocalDateTime.of(2020, Month.OCTOBER, 19, 20, 23, 54), "dinner", 900);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.NOVEMBER, 20, 10, 23, 30), "breakfast", 1000);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(firstMeal);
        meal.setDescription("updatedMeal");
        meal.setCalories(300);
        meal.setDateTime(LocalDateTime.of(2020, Month.NOVEMBER, 20, 10, 30));
        return meal;
    }
}
