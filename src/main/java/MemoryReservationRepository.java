import java.util.ArrayList;
import java.util.List;

public class MemoryReservationRepository implements IReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public boolean existsByUserAndBook(String userId, String bookId) {
        return reservations.stream()
                .anyMatch(r -> r.getUserId().equals(userId) && r.getBookId().equals(bookId));
    }

    @Override
    public List<Reservation> findByUser(String userId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getUserId().equals(userId)) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public void delete(String userId, String bookId) {
        reservations.removeIf(r -> r.getUserId().equals(userId) && r.getBookId().equals(bookId));
    }
}
