// Represents a user of the library
public class User {
    private String id;
    private String name;
    private final boolean isPriority; //

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.isPriority = false; // default value
    }
    public User(String id, boolean isPriority) {
        this.id = id;
        this.isPriority = isPriority;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isPriority() { return isPriority; }
}
