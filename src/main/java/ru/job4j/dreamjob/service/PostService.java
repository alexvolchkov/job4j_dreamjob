package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDbStore;

import java.util.Collection;

@ThreadSafe
@Service
public final class PostService {
    private final PostDbStore store;

    public PostService(PostDbStore store) {
        this.store = store;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public Post add(Post post) {
        return store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public boolean update(Post post) {
        return store.update(post);
    }
}
