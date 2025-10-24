// Interface for user repository
public interface IUserRepository {
    User findById(String userId);
    void save(User user);
}
