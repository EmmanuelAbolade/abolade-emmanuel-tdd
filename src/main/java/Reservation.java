// Represents a reservation made by a user for a book
public class Reservation {
    private String userId;
    private String bookId;

    public Reservation(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }
}
