package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    public PostStore() {
        int id = counter.getAndIncrement();
        posts.put(id, new Post(id, "Junior Java Job", "Junior Java Job", LocalDate.now()));
        id = counter.getAndIncrement();
        posts.put(id, new Post(id, "Middle Java Job", "Middle Java Job", LocalDate.now()));
        id = counter.getAndIncrement();
        posts.put(id, new Post(id, "Senior Java Job", "Senior Java Job", LocalDate.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post add(Post post) {
        return posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Post update(Post post) {
        return add(post);
    }

    public int getAndIncrement() {
        return counter.getAndIncrement();
    }
}
