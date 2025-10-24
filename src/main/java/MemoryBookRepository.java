import java.util.ArrayList;
import java.util.List;

// In-memory implementation of IBookRepository for testing
public class MemoryBookRepository implements IBookRepository {

    private final List<Book> books = new ArrayList<>();

    @Override
    public Book findById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void save(Book book) {
        // Remove any existing book with the same ID
        books.removeIf(b -> b.getId().equals(book.getId()));
        books.add(book);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }
}
