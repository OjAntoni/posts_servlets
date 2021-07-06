package storage.DbStorage;

import entity.Like;
import entity.Post;
import entity.User;
import storage.storage_interfaces.LikeStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbLikeStorage extends DbStorage implements LikeStorage {

    DbUserStorage dbUserStorage = new DbUserStorage();

    public DbLikeStorage(){
        super();
    }

    @Override
    public void save(int postId, Like like) {
        if(like==null) return;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into likes values (default ,?) returning id");
            preparedStatement.setInt(1,like.getAuthor().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            like.setId(id);
            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into posts_likes values(?,?)");
            preparedStatement1.setInt(1,postId);
            preparedStatement1.setInt(2,like.getId());
            preparedStatement1.execute();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Like like) {
        if(like==null) return;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from likes where id=?");
            preparedStatement.setInt(1,like.getId());
            preparedStatement.execute();
            PreparedStatement preparedStatement1 = connection.prepareStatement("delete from posts_likes where like_id=?");
            preparedStatement1.setInt(1,like.getId());
            preparedStatement1.execute();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public List<Like> getLikes(Post post) {
        return getLikesByPostId(post.getId());
    }

    @Override
    public boolean exists(User author, int postId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select l.user_id from likes l " +
                    "                           join (select like_id from posts_likes where post_id=?) X on X.like_id = l.id " +
                    "                           where user_id=?");
            preparedStatement.setInt(1,postId);
            preparedStatement.setInt(2,author.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public List<Like> getLikesByPostId(int postId){
        List<Like> likes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select like_id from posts_likes where post_id =?");
            preparedStatement.setInt(1,postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> likes_id = new ArrayList<>();
            while (resultSet.next()){
                likes_id.add(resultSet.getInt(1));
            }

            for (Integer like_id : likes_id) {
                PreparedStatement preparedStatement1 = connection.prepareStatement("select user_id from likes where id=?");
                preparedStatement1.setInt(1,like_id);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                int user_id;
                while (resultSet1.next()){
                    user_id = resultSet1.getInt(1);
                    User userById = dbUserStorage.getUserById(user_id);
                    likes.add(new Like(userById,like_id));
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return likes;
    }
}
