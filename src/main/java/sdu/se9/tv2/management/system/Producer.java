package sdu.se9.tv2.management.system;

public class Producer {
    private int id;
    private String name;

    public Producer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() { return id; }

    public String getName() {
        return name;
    }
}
