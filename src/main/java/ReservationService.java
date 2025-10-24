import java.util.ArrayList;
import java.util.List;


// Coordinates book reservations
public class ReservationService {

    private final IBookRepository bookRepo;
    private final IReservationRepository reservationRepo;
    private final IUserRepository userRepo; // Added for reserve() (Required for priority user logic)
    private final IWaitingListRepository waitingListRepo; // Added for managing priority waiting list


    // Constructor to inject all repositories
    public ReservationService(IBookRepository bookRepo, IReservationRepository reservationRepo, IUserRepository userRepo, IWaitingListRepository waitingListRepo) {
        this.bookRepo = bookRepo;
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.waitingListRepo = waitingListRepo;
    }

    // Cancels a reservation and increases the book's available copies
    public void cancel(String userId, String bookId) {
        // Check if the reservation exists
        if (!reservationRepo.existsByUserAndBook(userId, bookId)) {
            return; // Do nothing if no reservation found
        }

        // Retrieve the book being cancelled
        Book book = bookRepo.findById(bookId);

        // Increase the number of available copies
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);

        // Save the updated book back to the repository
        bookRepo.save(book);

        // Remove the reservation from the repository
        reservationRepo.delete(userId, bookId);
    }
    // Returns a list of books reserved by the given user
    public List<Book> findReservationsByUser(String userId) {
        // Retrieve all reservations made by the user
        List<Reservation> reservations = reservationRepo.findByUser(userId);

        // Map each reservation to its corresponding book
        List<Book> reservedBooks = new ArrayList<>();
        for (Reservation r : reservations) {
            Book book = bookRepo.findById(r.getBookId());
            if (book != null) {
                reservedBooks.add(book);
            }
        }

        return reservedBooks;
    }

    // Attempts to reserve a book for a user.
    // Preconditions:
    // - Book must exist.
    // - User must not have already reserved the same book.
    // - If no copies are available, only priority users may reserve.
    // Postconditions:
    // - Book's available copies are reduced (if not priority user).
    // - A reservation is created and saved.
    public void reserve(String userId, String bookId) {
        // Retrieve the book from the repository
        Book book = bookRepo.findById(bookId);

        // Check if the user has already reserved this book
        if (reservationRepo.existsByUserAndBook(userId, bookId)) {
            throw new IllegalStateException("User " + userId + " has already reserved book " + bookId);
        }

        // If no copies are available, check if the user is a priority user
        if (book.getCopiesAvailable() <= 0) {
            User user = userRepo.findById(userId);
            if (user == null || !user.isPriority()) {
                throw new NoAvailableCopiesException("No copies available for book: " + bookId);
            }
            // Priority user is allowed to reserve even if no copies
            // Priority user is added to waiting list
            waitingListRepo.addToWaitingList(bookId, userId);
            return; // Exit early, reservation will be handled later

        } else {
            // Decrease available copies for regular reservations
            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
            bookRepo.save(book);
        }

        // Create and save the reservation
        Reservation reservation = new Reservation(userId, bookId);
        reservationRepo.save(reservation);
    }



}
