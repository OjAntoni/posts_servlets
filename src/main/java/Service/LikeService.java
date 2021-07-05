package Service;

import Entity.Like;
import Entity.Post;
import Entity.User;
import Storage.storage_interfaces.LikeStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LikeService {
    LikeStorage storage;

    public LikeService(LikeStorage storage) {
        this.storage = storage;
    }

    public void add(Like like, int postId){
        if(like==null) return;
        storage.save(postId, like);
    }

    public void remove(int likeId, int postId){
        storage.delete(new Like(null,likeId));
    }

    public boolean exists(User author, int postId){
        if(author==null) return true;
        return storage.exists(author, postId);
    }

    public List<Like> getLikesForPost(int postId){
        return storage.getLikes(new Post(postId,null,null,null,null,null));
    }

    public Optional<Like> getLikeForPost(int postId, int userId){
        List<Like> collection = getLikesForPost(postId).stream().filter(l -> l.getAuthor().getId() == userId).collect(Collectors.toList());
        if(collection.isEmpty()) return Optional.empty();
        return Optional.of(collection.get(0));
    }
}
