package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    public void addMeal(Meal meal);

    public void deleteMeal(Integer id);

    public void updateMeal(Meal meal);

    public List<Meal> getAllMeals();

    public Meal getMealById(Integer id);
}
