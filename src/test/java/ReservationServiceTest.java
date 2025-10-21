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
    }

    // Test that a reservation is created for the user
    // This test checks that a reservation entry is created for the user
    @Test
    void testReserveCreatesReservation() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Create and save a book
        Book book = new Book("b1", "Clean Code", 2);
        bookRepo.save(book);

        // Act: Reserve the book
        service.reserve("u1", "b1");

        // Assert: Reservation should exist
        assertTrue(reservationRepo.existsByUserAndBook("u1", "b1"));
    }

    // Test for exception when no copies available
    // This test checks that an exception is thrown when trying to reserve a book with 0 copies
    @Test
    void testReserveFailsWhenNoCopiesAvailable() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Create and save a book with 0 available copies
        Book book = new Book("b2", "Refactoring", 0);
        bookRepo.save(book);

        // Act & Assert: Expect NoAvailableCopiesException
        assertThrows(NoAvailableCopiesException.class, () -> service.reserve("u2", "b2"));
    }


}
