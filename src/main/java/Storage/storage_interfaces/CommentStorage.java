package Storage.storage_interfaces;

import Entity.Comment;
import Entity.Post;

import java.util.List;

public interface CommentStorage {
    void save(Comment comment, int postId);
    void delete(Comment comment);
    void upgradeComment(Comment comment, String newCommentText);
    List<Comment> getComments(Post post);
    Comment getComment(int id);
    void approve(Comment comment);
    void disapprove(Comment comment);
}
