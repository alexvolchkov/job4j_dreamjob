package ru.job4j.dreamjob.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class PostDbStoreTest {

    @After
    public void CleanDB() {
        CityService cityService = new CityService();
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        store.deleteAll();
    }

    @Test
    public void whenCreatePost() {
        CityService cityService = new CityService();
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        Post post = new Post(0, "Java Job", cityService.findById(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        CityService cityService = new CityService();
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        Post post = new Post(0, "Java Job", cityService.findById(1));
        store.add(post);
        post.setName("Update name");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenFindByIdPost() {
        CityService cityService = new CityService();
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        Post post1 = new Post(0, "Post1", cityService.findById(1));
        Post post2 = new Post(1, "Post2", cityService.findById(1));
        store.add(post1);
        store.add(post2);
        Post postInDb = store.findById(post1.getId());
        assertThat(postInDb.getName(), is(post1.getName()));
    }

    @Test
    public void whenDeleteAll() {
        CityService cityService = new CityService();
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        Post post1 = new Post(0, "Post1", cityService.findById(1));
        Post post2 = new Post(1, "Post2", cityService.findById(1));
        store.add(post1);
        store.add(post2);
        store.deleteAll();
        assertThat(store.findAll().size(), is(0));
    }

    @Test
    public void whenFindAllPost() {
        CityService cityService = new CityService();
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        Post post1 = new Post(0, "Post1", cityService.findById(1));
        Post post2 = new Post(1, "Post2", cityService.findById(1));
        store.add(post1);
        store.add(post2);
        List<Post> rsl = store.findAll();
        assertThat(rsl.size(), is(2));
        assertTrue(rsl.containsAll(List.of(post1, post2)));
    }
}