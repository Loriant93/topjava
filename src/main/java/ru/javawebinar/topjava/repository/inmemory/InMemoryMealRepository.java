package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        log.info("save {} : userId{}", meal, userId);

        if (meal.isNew()) {
            meal.setId(counter.getAndIncrement());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (!repository.containsKey(meal.getId()))
            return null;
        if (!repository.get(meal.getId()).getUserId().equals(userId))
            return null;
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        log.info("delete mealId{} : userId{}", id, userId);


        if (repository.containsKey(id) && !userId.equals(repository.get(id).getUserId()))
            return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        log.info("get mealId{} : userId{}", id, userId);

        if (repository.containsKey(id) && !userId.equals(repository.get(id).getUserId()))
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        log.info("getAll userId{}", userId);

        return repository.values().stream().filter(m -> userId.equals(m.getUserId()))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

