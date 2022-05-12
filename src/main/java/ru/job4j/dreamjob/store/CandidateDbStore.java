package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(createCandidateFromDB(it));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate (name, description, created, photo) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    public boolean update(Candidate candidate) {
        boolean rsl = true;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE candidate SET name = ?, description = ?, created = ?, photo = ? WHERE id = ?")
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            rsl = false;
        }
        return rsl;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createCandidateFromDB(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Candidate createCandidateFromDB(ResultSet it) throws SQLException {
        return new Candidate(
                it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
               it.getTimestamp("created").toLocalDateTime(),
                it.getBytes("photo"));
    }

    public void deleteAll() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM candidate")
        ) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
