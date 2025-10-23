import java.util.List;

// Interface to store and retrieve books
public interface IBookRepository {
    Book findById(String id);       // Find a book by its ID
    void save(Book book);           // Save or update a book
    List<Book> findAll();
}
