import java.util.List;

// Interface to store and manage reservations
public interface IReservationRepository {
        void save(Reservation reservation);                         // Save a reservation
        boolean existsByUserAndBook(String userId, String bookId);  // Check if reservation exists
        List<Reservation> findByUser(String userId);                // List reservations by user
        void delete(String userId, String bookId);                  // Cancel a reservation
}
