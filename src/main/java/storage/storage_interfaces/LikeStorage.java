package storage.storage_interfaces;

import entity.Like;
import entity.Post;
import entity.User;

import java.util.List;

public interface LikeStorage {
    void save(int postId, Like like);
    void delete(Like like);
    List<Like> getLikes(Post post);
    boolean exists(User author, int postId);
}
