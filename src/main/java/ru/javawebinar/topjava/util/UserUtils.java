package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UserUtils {

    public static final List<User> users = Arrays.asList(
            new User("Ivan","1111@gmail.com","trom", Role.USER),
            new User("Aleks","2222@gmail.com","qwer", Role.USER,Role.ADMIN),
            new User("Petr","3333@gmail.com","lock", Role.ADMIN),
            new User("Lena","5555@yandex.ru","port", Role.USER)
    );
}
