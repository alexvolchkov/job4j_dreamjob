package ru.job4j.dreamjob.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class UserDbStoreTest {

    @After
    public void CleanDB() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        store.deleteAll();
    }

    @Test
    public void whenCreateUser() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user = new User(0, "User", "email");
        store.add(user);
        User userInDb = store.findById(user.getId());
        assertThat(userInDb.getName(), is(user.getName()));
    }

    @Test
    public void whenNotCreateUser() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user1 = new User(0, "User1", "email");
        User user2 = new User(0, "User2", "email");
        store.add(user1);
        assertTrue(store.add(user2).isEmpty());
    }

    @Test
    public void whenUpdateUser() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user = new User(0, "User", "email");
        store.add(user);
        user.setName("Update name");
        store.update(user);
        User userInDb = store.findById(user.getId());
        assertThat(userInDb.getName(), is(user.getName()));
    }

    @Test
    public void whenFindByIdUser() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user1 = new User(0, "User1", "email1");
        User user2 = new User(0, "User2", "email2");
        store.add(user1);
        store.add(user2);
        User userInDb = store.findById(user1.getId());
        assertThat(userInDb.getName(), is(user1.getName()));
    }

    @Test
    public void whenDeleteAll() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user1 = new User(0, "User1", "email1");
        User user2 = new User(0, "User2", "email2");
        store.add(user1);
        store.add(user2);
        store.deleteAll();
        assertThat(store.findAll().size(), is(0));
    }

    @Test
    public void whenFindAllPost() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user1 = new User(0, "User1", "email1");
        User user2 = new User(0, "User2", "email2");
        store.add(user1);
        store.add(user2);
        List<User> rsl = store.findAll();
        assertThat(rsl.size(), is(2));
        assertTrue(rsl.containsAll(List.of(user1, user2)));
    }

    @Test
    public void whenFindUserByEmailAndPwd() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user1 = new User(0, "User1", "email1", "12");
        User user2 = new User(0, "User2", "email2", "34");
        store.add(user1);
        store.add(user2);
        User userInDb = store.findUserByEmailAndPwd(user1.getEmail(), user1.getPassword()).get();
        assertThat(userInDb.getName(), is(user1.getName()));
    }

    @Test
    public void whenNotFindUserByEmailAndPwd() {
        UserDbStore store = new UserDbStore(new Main().loadPool());
        User user1 = new User(0, "User1", "email1", "12");
        User user2 = new User(0, "User2", "email2", "34");
        store.add(user1);
        store.add(user2);
        assertTrue(store.findUserByEmailAndPwd(user1.getEmail(), "11").isEmpty());
        assertTrue(store.findUserByEmailAndPwd("email", user1.getPassword()).isEmpty());
        assertTrue(store.findUserByEmailAndPwd("email", "11").isEmpty());
    }
}