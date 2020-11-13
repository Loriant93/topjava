package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static org.junit.Assert.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void testGet() {
        Meal meal = service.get(FIRST_MEAL_ID, USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(firstMeal);
    }

    @Test
    public void testDelete() {
        service.delete(FIRST_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(FIRST_MEAL_ID, USER_ID));
    }

    @Test
    public void testGetBetweenInclusive() {
        List<Meal> mealList = service.getBetweenInclusive(LocalDate.of(2020, Month.OCTOBER, 18),
                LocalDate.of(2020, Month.OCTOBER, 18), USER_ID);
        assertThat(mealList).usingRecursiveComparison().isEqualTo(Collections.singletonList(firstMeal));
    }

    @Test
    public void testGetAll() {
        List<Meal> mealList = service.getAll(USER_ID);
        assertThat(mealList).usingRecursiveComparison().isEqualTo(Arrays.asList(secondMeal, firstMeal));
    }

    @Test
    public void testUpdate() {
        Meal updatedMeal = MealTestData.getUpdated();
        service.update(updatedMeal, USER_ID);
        assertThat(service.get(FIRST_MEAL_ID, USER_ID)).usingRecursiveComparison().isEqualTo(updatedMeal);
    }

    @Test
    public void testCreate() {
        Meal newMeal = MealTestData.getNew();
        Meal createdMeal = service.create(newMeal, UserTestData.USER_ID);
        newMeal.setId(createdMeal.getId());
        assertThat(newMeal).usingRecursiveComparison().isEqualTo(createdMeal);
        assertThat(service.get(createdMeal.getId(), UserTestData.USER_ID)).usingRecursiveComparison().isEqualTo(newMeal);
    }

    @Test
    public void deleteAnothersMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(FIRST_MEAL_ID, ADMIN_ID));
    }

    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void getAnothersMeal() {
        assertThrows(NotFoundException.class, () -> service.get(FIRST_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void updateAnothersMeal() {
        Meal updatedMeal = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updatedMeal, ADMIN_ID));
        assertThat(service.get(FIRST_MEAL_ID, USER_ID)).usingRecursiveComparison().isNotEqualTo(updatedMeal);
    }
}