package Entity;

import java.util.Objects;

public class Comment{
    private int id;
    private final User u;
    private String text;
    boolean approved;
    private boolean idChangeAllow;

    public Comment(int id, User u, String text) {
        this.id = id;
        this.u = u;
        this.text = text;
        approved = false;
        idChangeAllow=true;
    }

    public Comment(int id, User u, String text, boolean approved) {
        this.id = id;
        this.u = u;
        this.text = text;
        this.approved = approved;
        idChangeAllow=true;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return u;
    }

    public String getText() {
        return text;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setId(int id) {
        if(idChangeAllow){
            this.id = id;
            idChangeAllow=false;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", u=" + u +
                ", text='" + text + '\'' +
                '}';
    }
}
