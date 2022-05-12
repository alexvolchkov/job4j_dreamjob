package ru.job4j.dreamjob.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class CandidateDbStoreTest {

    @After
    public void CleanDB() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        store.deleteAll();
    }

    @Test
    public void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Candidate");
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Candidate");
        store.add(candidate);
        candidate.setName("Update name");
        store.update(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenFindByIdCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(0, "Candidate1");
        Candidate candidate2 = new Candidate(1, "Candidate2");
        store.add(candidate1);
        store.add(candidate2);
        Candidate candidateInDb = store.findById(candidate1.getId());
        assertThat(candidateInDb.getName(), is(candidate1.getName()));
    }

    @Test
    public void whenDeleteAll() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(0, "Candidate1");
        Candidate candidate2 = new Candidate(1, "Candidate2");
        store.add(candidate1);
        store.add(candidate2);
        store.deleteAll();
        assertThat(store.findAll().size(), is(0));
    }

    @Test
    public void whenFindAllCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(0, "Candidate1");
        Candidate candidate2 = new Candidate(1, "Candidate2");
        store.add(candidate1);
        store.add(candidate2);
        List<Candidate> rsl = store.findAll();
        assertThat(rsl.size(), is(2));
        assertTrue(rsl.containsAll(List.of(candidate1, candidate2)));
    }
}