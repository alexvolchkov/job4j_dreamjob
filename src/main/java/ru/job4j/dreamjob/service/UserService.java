package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDbStore;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public final class UserService {
    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public Collection<User> findAll() {
        return store.findAll();
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public User findById(int id) {
        return store.findById(id);
    }

    public boolean update(User user) {
        return store.update(user);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }
}
