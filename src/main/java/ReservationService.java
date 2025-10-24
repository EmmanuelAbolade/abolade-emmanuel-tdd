// Coordinates book reservations
public class ReservationService {

    private final IBookRepository bookRepo;
    private final IReservationRepository reservationRepo;

    public ReservationService(IBookRepository bookRepo, IReservationRepository reservationRepo) {
        this.bookRepo = bookRepo;
        this.reservationRepo = reservationRepo;
    }

    // Reserve a book for a user (with rule: must not already be reserved)
    public void reserve(String userId, String bookId) {
        Book book = bookRepo.findById(bookId);

        //Cannot reserve if no copies available
        if (book.getCopiesAvailable() <= 0) {
            throw new NoAvailableCopiesException("No copies available for book: " + bookId);
        }

        //Cannot reserve the same book twice
        if (reservationRepo.existsByUserAndBook(userId, bookId)) {
            throw new IllegalStateException("User " + userId + " has already reserved book " + bookId);
        }

        //Decrease available copies
        int updatedCopies = book.getCopiesAvailable() - 1;
        book.setCopiesAvailable(updatedCopies);
        bookRepo.save(book);

        // Save the reservation
        Reservation reservation = new Reservation(userId, bookId);
        reservationRepo.save(reservation);

    }

    // Cancels a reservation and increases the book's available copies
    public void cancel(String userId, String bookId) {
        // Retrieve the book being cancelled
        Book book = bookRepo.findById(bookId);

        // Increase the number of available copies
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);

        // Save the updated book back to the repository
        bookRepo.save(book);

        // Remove the reservation from the repository
        reservationRepo.delete(userId, bookId);
    }


}
