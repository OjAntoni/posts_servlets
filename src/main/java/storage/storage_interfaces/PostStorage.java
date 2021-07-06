package storage.storage_interfaces;

import entity.Post;

import java.util.List;

public interface PostStorage {
    void save(Post post);
    void delete(Post post);
    void upgradePost(Post post, String newDescription);
    List<Post> getPosts();
    void approve(Post post);
    void disapprove(Post post);
}
