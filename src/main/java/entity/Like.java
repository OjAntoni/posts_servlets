package entity;

import java.util.Objects;

public class Like {
    private User author;
    private int id;
    private boolean idChangeAllow;

    public Like(User author, int id) {
        this.author = author;
        this.id = id;
        idChangeAllow=true;
    }

    public User getAuthor() {
        return author;
    }

    public int getId() {
        return id;
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
        Like like = (Like) o;
        return id == like.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Like{" +
                "author=" + author +
                ", id=" + id +
                ", idChangeAllow=" + idChangeAllow +
                '}';
    }
}
