import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class for ReservationService
public class ReservationServiceTest {
    // Reserve a book successfully
    @Test
    void testReserveBookSuccessfully() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Create and save a book with 2 available copies
        Book book = new Book("b1", "Clean Code", 2);
        bookRepo.save(book);
        // Attempt to reserve the book for user u1
        service.reserve("u1", "b1");

        // Assert that one copy is now used and reservation exists
        assertEquals(1, bookRepo.findById("b1").getCopiesAvailable());
        //assertTrue(reservationRepo.existsByUserAndBook("u1", "b1"));
    }

    // Test that a reservation is created for the user
    @Test
    void testReserveCreatesReservation() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        Book book = new Book("b1", "Clean Code", 2);
        bookRepo.save(book);

        service.reserve("u1", "b1");

        assertTrue(reservationRepo.existsByUserAndBook("u1", "b1"));
    }


}
