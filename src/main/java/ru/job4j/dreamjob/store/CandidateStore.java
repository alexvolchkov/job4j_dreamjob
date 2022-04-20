package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(4);

    public CandidateStore() {
        candidates.put(1, new Candidate(1, "Alex", "Junior Java Job", LocalDate.now()));
        candidates.put(2, new Candidate(2, "Oleg", "Middle Java Job", LocalDate.now()));
        candidates.put(3, new Candidate(3, "Ivan", "Senior Java Job", LocalDate.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate add(Candidate candidate) {
        candidate.setId(counter.getAndIncrement());
        return candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public boolean update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate) != null;
    }
}
