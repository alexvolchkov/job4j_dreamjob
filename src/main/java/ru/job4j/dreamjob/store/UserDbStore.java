package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDbStore {
    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(createUserFromDB(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            rsl = Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public boolean update(User user) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            rsl = ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public User findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createUserFromDB(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private User createUserFromDB(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("name"),
                it.getString("email"),
                it.getString("password"));
    }

    public void deleteAll() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM users")
        ) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
