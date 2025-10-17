// Represents a book in the library
public class Book {
    private String id;
    private String title;
    private int copiesAvailable;

    public Book(String id, String title, int copiesAvailable) {
        this.id = id;
        this.title = title;
        this.copiesAvailable = copiesAvailable;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getCopiesAvailable() { return copiesAvailable; }

    // Updates the number of available copies
    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }
}
