package ru.job4j.dreamjob.store;

import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserDbStoreTest {

    @Test
    @Ignore
    public void whenCreateUser() {

    }

    @Test
    public void findAll() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user = new User(0, "Alex", "11@mm.ru");
        store.add(user);
        System.out.println(user.getName());
        System.out.println(user.getId());
        List<User> users = store.findAll();
        for (User user1 : users) {
            System.out.println(user1.getId());
        }

    }

    @Test
    @Ignore
    public void add() {
    }

    @Test
    @Ignore
    public void update() {
    }
}