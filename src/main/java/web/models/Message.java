package web.models;

import java.sql.Time;
import java.time.LocalTime;

public class Message {
    private String text;
    private Time time;
    private String author;

    public void setTime(Time time) {
        this.time = time;
    }

    public Message() {
       setTime();
    }

    public String getText() {
        return text;
    }

    public Time getTime() {
        return time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime() {
        var localTime = LocalTime.now();
        this.time = Time.valueOf(localTime);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
