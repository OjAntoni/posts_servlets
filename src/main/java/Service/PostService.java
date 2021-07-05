package Service;

import Entity.Post;
import Storage.storage_interfaces.PostStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {
    PostStorage storage;

    public PostService(PostStorage storage) {
        this.storage = storage;
    }

    public boolean save(Post post){
        if(post==null) return false;
        storage.save(post);
        return true;
    }

    public boolean delete(Post post){
        if(post==null) return false;
        storage.delete(post);
        return true;
    }

    public boolean delete(int postId){
        if(postId<=0) return false;
        storage.delete(new Post(postId,null,null,null,null,null));
        return true;
    }

    public boolean updatePost(Post post, String newDescription){
        if(post==null||newDescription==null) return false;
        storage.upgradePost(post, newDescription);
        return true;
    }

    public boolean updatePost(int postId, String newDescription){
        if(postId<=0||newDescription==null) return false;
        storage.upgradePost(new Post(postId,null,null,null,null,null), newDescription);
        return true;
    }

    public List<Post> getApprovedPosts(){
        List<Post> allPosts = getAllPosts();
        return allPosts.stream().filter(Post::isApproved).collect(Collectors.toList());
    }

    public List<Post> getAllPosts(){
        return storage.getPosts();
    }

    public List<Post> getDisapprovedPosts(){
        List<Post> allPosts = getAllPosts();
        return allPosts.stream().filter(p -> !p.isApproved()).collect(Collectors.toList());
    }

    public boolean approve(Post post){
        if(post==null) return false;
        if(!exists(post)){
            storage.approve(post);
            return true;
        }
        return false;
    }

    public boolean approve(int postId){
        return approve(new Post(postId,null,null,null,null,null));
    }

    public boolean disapprove(Post post){
        if(post==null) return false;
        if(!exists(post)){
            storage.disapprove(post);
            return true;
        }
        return false;
    }

    public boolean disapprove(int postId){
        return disapprove(new Post(postId,null,null,null,null,null));
    }

    public boolean exists(Post p){
        int id = p.getId();
        ArrayList<Post> posts = new ArrayList<>(storage.getPosts());
        for (Post post : posts) {
            if(post.getId()==id) return true;
        }
        return false;
    }

    public boolean exists(int id){
        ArrayList<Post> posts = new ArrayList<>(storage.getPosts());
        for (Post post : posts) {
            if(post.getId()==id) return true;
        }
        return false;
    }
}
