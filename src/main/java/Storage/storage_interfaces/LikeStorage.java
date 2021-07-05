package Storage.storage_interfaces;

import Entity.Like;
import Entity.Post;
import Entity.User;

import java.util.List;

public interface LikeStorage {
    void save(int postId, Like like);
    void delete(Like like);
    List<Like> getLikes(Post post);
    boolean exists(User author, int postId);
}
