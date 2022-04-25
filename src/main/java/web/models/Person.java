package web.models;

public class Person {
    //todo get max id from db
    private static int idGetter = 0;
    private final int id;
    private String name;

    public Person(String name) {
        id = idGetter++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
