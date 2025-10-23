// Coordinates book reservations
public class ReservationService {

    private final IBookRepository bookRepo;
    private final IReservationRepository reservationRepo;

    public ReservationService(IBookRepository bookRepo, IReservationRepository reservationRepo) {
        this.bookRepo = bookRepo;
        this.reservationRepo = reservationRepo;
    }

    // Reserve a book for a user (happy path only)
    public void reserve(String userId, String bookId) {
        Book book = bookRepo.findById(bookId);

        //Cannot reserve if no copies available
        if (book.getCopiesAvailable() <= 0) {
            throw new NoAvailableCopiesException("No copies available for book: " + bookId);
        }

        //Decrease available copies
        int updatedCopies = book.getCopiesAvailable() - 1;
        book.setCopiesAvailable(updatedCopies);
        bookRepo.save(book);

        // Save the reservation
        Reservation reservation = new Reservation(userId, bookId);
        reservationRepo.save(reservation);


    }
}
