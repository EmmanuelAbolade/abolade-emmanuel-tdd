import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


// Test class for ReservationService (Reserving the last copy)
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

    // Test for exception when no copies available (Attempting to reserve with no copies left)
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

    // Test that a user cannot reserve the same book twice
    @Test
    void testReserveFailsIfAlreadyReserved() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Book with 2 copies
        Book book = new Book("b3", "Domain-Driven Design", 2);
        bookRepo.save(book);

        // First reservation succeeds
        service.reserve("u3", "b3");

        // Second reservation by same user should fail
        assertThrows(IllegalStateException.class, () -> service.reserve("u3", "b3"));
    }

    // test: cancel() increases copiesAvailable
    @Test
    void cancelShouldIncreaseCopiesAvailable() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Book with 1 copy, reserved by user
        Book book = new Book("b4", "Effective Java", 1);
        bookRepo.save(book);
        service.reserve("u4", "b4");

        // Act: Cancel the reservation
        service.cancel("u4", "b4");

        // Assert: copiesAvailable should be back to 1
        assertEquals(1, bookRepo.findById("b4").getCopiesAvailable());
    }
    // test: cancel() should do nothing if reservation doesn't exist (cancelling non-existent resrvations)
    @Test
    void cancelShouldDoNothingIfReservationDoesNotExist() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Book with 1 copy, no reservation made
        Book book = new Book("b5", "Clean Code", 1);
        bookRepo.save(book);

        // Act: Attempt to cancel a non-existent reservation
        service.cancel("u5", "b5");

        // Assert: copiesAvailable should remain unchanged
        assertEquals(1, bookRepo.findById("b5").getCopiesAvailable());
    }
    // test: cancel() should remove the reservation
    @Test
    void cancelShouldRemoveReservation() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Book reserved by user
        Book book = new Book("b6", "Refactoring", 1);
        bookRepo.save(book);
        service.reserve("u6", "b6");

        // Act: Cancel the reservation
        service.cancel("u6", "b6");

        // Assert: Reservation should no longer exist
        assertFalse(reservationRepo.existsByUserAndBook("u6", "b6"));
    }

    // Failing test: findReservationsByUser() should return books reserved by the user (User can view all their reservations)
    @Test
    void findReservationsByUserShouldReturnCorrectBooks() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Add books and reservations
        Book book1 = new Book("b7", "Domain-Driven Design", 2);
        Book book2 = new Book("b8", "The Pragmatic Programmer", 1);
        bookRepo.save(book1);
        bookRepo.save(book2);
        service.reserve("u7", "b7");
        service.reserve("u7", "b8");

        // Act: Retrieve reserved books
        List<Book> reservedBooks = service.findReservationsByUser("u7");

        // Assert: Should contain both books
        assertEquals(2, reservedBooks.size()); //checks the book count
        assertTrue(reservedBooks.stream().anyMatch(b -> b.getId().equals("b7")));
        assertTrue(reservedBooks.stream().anyMatch(b -> b.getId().equals("b8")));
    }

    // Failing test: priority user should be able to reserve even if no copies are available
    @Test
    void priorityUserCanReserveWhenNoCopiesAvailable() {
        IBookRepository bookRepo = new MemoryBookRepository();
        IReservationRepository reservationRepo = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepo, reservationRepo);

        // Arrange: Book with 0 copies
        Book book = new Book("b9", "Working Effectively with Legacy Code", 0);
        bookRepo.save(book);

        // Act: Priority user attempts to reserve
        User priorityUser = new User("u9", true); // true = isPriority
        service.reserve(priorityUser.getId(), book.getId());

        // Assert: Reservation should exist despite no available copies
        assertTrue(reservationRepo.existsByUserAndBook("u9", "b9"));
    }


}
