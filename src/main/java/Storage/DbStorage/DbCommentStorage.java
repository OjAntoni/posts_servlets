package Storage.DbStorage;

import Entity.Comment;
import Entity.Post;
import Entity.User;
import Storage.storage_interfaces.CommentStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbCommentStorage extends DbStorage implements CommentStorage {

    DbUserStorage dbUserStorage = new DbUserStorage();

    public DbCommentStorage(){
        super();
    }

    @Override
    public void save(Comment comment, int postId) {
        if(comment==null) return;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into comments values(DEFAULT,?,false) returning id");
            preparedStatement.setString(1,comment.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int commentId = resultSet.getInt(1);
            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into comments_users values(?,?)");
            preparedStatement1.setInt(1,commentId);
            preparedStatement1.setInt(2,comment.getUser().getId());
            preparedStatement1.execute();
            PreparedStatement preparedStatement2 = connection.prepareStatement("insert into posts_comments values(?,?)");
            preparedStatement2.setInt(1,postId);
            preparedStatement2.setInt(2,commentId);
            preparedStatement2.execute();
            connection.commit();
            comment.setId(commentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Comment comment) {
        if (comment==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from comments where id=" + comment.getId() + "; " +
                    "                       delete from posts_comments where comment_id=" + comment.getId() + "; " +
                    "                       delete from comments_users where comment_id=" + comment.getId() + "; ");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void upgradeComment(Comment comment, String newCommentText) {
        if(comment==null||newCommentText==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update comments set text = ? where id=" + comment.getId() + "; ");
            preparedStatement.setString(1,newCommentText);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Comment> getComments(Post post) {
        List<Comment> comments  = new ArrayList<>();
        if(post!=null){
            int post_id = post.getId();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select comment_id from posts_comments where post_id=" + post_id + "; ");
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Integer> comments_id = new ArrayList<>();
                while (resultSet.next()){
                    comments_id.add(resultSet.getInt(1));
                }
                for (Integer comment_id : comments_id) {
                    ResultSet resultSet1 = connection.prepareStatement("select * from comments where id=" + comment_id + "; ").executeQuery();
                    PreparedStatement preparedStatement1 = connection.prepareStatement("select user_id from comments_users where comment_id=" + comment_id + "; ");
                    ResultSet resultSet2 = preparedStatement1.executeQuery();
                    resultSet2.next();
                    int user_id = resultSet2.getInt(1);
                    User userById = dbUserStorage.getUserById(user_id);
                    if(resultSet1.next()){
                        int id = resultSet1.getInt(1);
                        String text = resultSet1.getString(2);
                        boolean approved = resultSet1.getBoolean(3);
                        comments.add(new Comment(id, userById, text, approved));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return comments;
    }

    @Override
    public Comment getComment(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from comments where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            int commentId = resultSet.getInt(1);
            String text = resultSet.getString(2);
            boolean approved = resultSet.getBoolean(3);

            PreparedStatement preparedStatement1 = connection.prepareStatement("select user_id from comments_users  where comment_id=?");
            preparedStatement1.setInt(1,id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            int userId = resultSet1.getInt(1);
            User user = dbUserStorage.getUserById(userId);
            return new Comment(commentId, user, text, approved);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void approve(Comment comment) {
        if(comment==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update comments set approved=true where id=" + comment.getId() + " ");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void disapprove(Comment comment) {
        if(comment==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update comments set approved=false where id=" + comment.getId() + " ");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
