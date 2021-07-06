package storage.DbStorage;

import entity.Comment;
import entity.Like;
import entity.Post;
import entity.User;
import storage.storage_interfaces.PostStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbPostStorage extends DbStorage implements PostStorage {

    DbUserStorage dbUserStorage = new DbUserStorage();
    DbLikeStorage dbLikeStorage = new DbLikeStorage();
    DbCommentStorage dbCommentStorage = new DbCommentStorage();

    public DbPostStorage(){
        super();
    }


    @Override
    public void save(Post post) {
        if(post==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into posts values (default ,?,?,?,false) returning id");
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getDescription());
            preparedStatement.setInt(3,post.getAuthor().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int postId = resultSet.getInt(1);
            post.setId(postId);
            if(post.getLikes()!=null){
                for (Like like : post.getLikes()) {
                    dbLikeStorage.save(post.getId(),like);
                }
            }
            if(post.getComments()!=null){
                for (Comment comment : post.getComments()) {
                    dbCommentStorage.save(comment, post.getId());
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Post post) {
        if(post==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from posts where id="+post.getId()+"; " +
                    "delete from posts_comments where post_id="+post.getId()+"; " +
                    "delete from posts_likes where post_id="+post.getId()+" ");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void upgradePost(Post post, String newDescription) {
        if(post==null||newDescription==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update posts set description=? where id=?");
            preparedStatement.setString(1,newDescription);
            preparedStatement.setInt(2,post.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from posts");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String descr = resultSet.getString(3);
                int author = resultSet.getInt(4);
                boolean approved = resultSet.getBoolean(5);
                User userById = dbUserStorage.getUserById(author);
                List<Like> likesByPostId = dbLikeStorage.getLikesByPostId(id);
                List<Comment> comments = dbCommentStorage.getComments(new Post(id, null, null, null, null, null));
                Post post = new Post(id,title,descr,userById,likesByPostId,comments,approved);
                posts.add(post);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    @Override
    public void approve(Post post) {
        if(post==null) return;
        int id = post.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update posts set approved=true where id=" + id + " ");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void disapprove(Post post) {
        if(post==null) return;
        int id = post.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update posts set approved=false where id=" + id + " ");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
