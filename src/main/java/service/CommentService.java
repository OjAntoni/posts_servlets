package service;

import entity.Comment;
import entity.Post;
import storage.storage_interfaces.CommentStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentService {
    private final CommentStorage storage;

    public CommentService(CommentStorage storage) {
        this.storage = storage;
    }

    public void addComment(Comment comment, int postId){
        storage.save(comment,postId);
    }

    public void deleteComment(Comment comment){
        storage.delete(comment);
    }

    public void deleteComment(int commentId){
        storage.delete(new Comment(commentId,null,null));
    }

    public List<Comment> getComments(Post post){
        return storage.getComments(post);
    }

    public List<Comment> getAllComments(int postId){
        return storage.getComments(new Post(postId,null,null,null,null,null));
    }

    public List<Comment> getApprovedComments(Post post){
        return storage.getComments(post).stream().filter(Comment::isApproved).collect(Collectors.toList());
    }

    public List<Comment> getApprovedComments(int postId){
        Post post = new Post(postId,null,null,null,null,null);
        return storage.getComments(post).stream().filter(Comment::isApproved).collect(Collectors.toList());
    }

    public List<Comment> getDisapprovedComments(Post post){
        return storage.getComments(post).stream().filter(com -> !com.isApproved()).collect(Collectors.toList());
    }

    public List<Comment> getDisapprovedComments(int postId){
        Post post = new Post(postId,null,null,null,null,null);
        return storage.getComments(post).stream().filter(com -> !com.isApproved()).collect(Collectors.toList());
    }

    public void updateComment(int id, String newText){
        storage.upgradeComment(new Comment(id,null,null),newText);
    }

    public Optional<Comment> getComment(int id){
        return Optional.ofNullable(storage.getComment(id));
    }

    public void approve(Comment comment){
        storage.approve(comment);
    }

    public void approve(int commentId){
        storage.approve(new Comment(commentId,null,null));
    }
}
