package web.models.messages;

import web.models.users.Person;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String text;
    private Date time;
    private int id;
    private String author;

    public void setTime(Date time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Message() {
       setTime();
    }

    public String getText() {
        return text;
    }

    public Date getTime() {
        return time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        time = new Date(System.currentTimeMillis());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
