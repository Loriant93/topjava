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
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0), "Завтрак", 2500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак", 500)
        );

        //    List<UserMealWithExcess> mealsTo = filteredByCycles2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //    mealsTo.forEach(System.out::println);

        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles1(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWE = new ArrayList<>();
        meals.sort(Comparator.comparing(UserMeal::getDateTime));

        for (int i = 0, j; i < meals.size(); ) {
            int caloriesForDay = meals.get(i).getCalories();
            if (i != meals.size() - 1)
                j = i + 1;
            else
                j = i;

            while (meals.get(i).getDateTime().toLocalDate().equals(meals.get(j).getDateTime().toLocalDate())) {
                caloriesForDay += meals.get(j).getCalories();
                j++;
                if (j == meals.size())
                    break;
            }

            for (; i < j; i++) {
                if (TimeUtil.isBetweenHalfOpen(meals.get(i).getDateTime().toLocalTime(), startTime, endTime))
                    userMealWE.add(new UserMealWithExcess(meals.get(i), caloriesForDay > caloriesPerDay));
            }

        }


        return userMealWE;
    }

    public static List<UserMealWithExcess> filteredByCycles2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWE = new ArrayList<>();
        Map<LocalDate, Integer> mealMap = new HashMap<>();

        for (UserMeal meal : meals) {
            mealMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                userMealWE.add(new UserMealWithExcess(meal, mealMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return userMealWE;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, final int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> mealMap = meals.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()));
        List<UserMealWithExcess> mealWithExcessList = new ArrayList<>();

        mealMap.forEach((localDate, userMeals) -> {
            final int calories = userMeals.stream().mapToInt(UserMeal::getCalories).sum();
            mealWithExcessList.addAll(
                    userMeals.stream().filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> new UserMealWithExcess(meal, calories > caloriesPerDay)).collect(Collectors.toList())
            );
        });

        return mealWithExcessList;
    }
}
