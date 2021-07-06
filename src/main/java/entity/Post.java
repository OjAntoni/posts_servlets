package entity;

import java.util.List;
import java.util.Objects;

public class Post {
    private int id;
    private String title;
    private String description;
    private User author;
    private List<Like> likes;
    private List<Comment> comments;
    boolean approved;
    private boolean idChangeAllow;

    public Post(int id, String title, String description, User author, List<Like> likes, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.likes = likes;
        this.comments = comments;
        idChangeAllow=true;
    }

    public Post(int id, String title, String description, User author, List<Like> likes, List<Comment> comments, boolean approved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.likes = likes;
        this.comments = comments;
        this.approved = approved;
        idChangeAllow=true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getAuthor() {
        return author;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setId(int id) {
        if(idChangeAllow){
            this.id = id;
            idChangeAllow=false;
        }

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
