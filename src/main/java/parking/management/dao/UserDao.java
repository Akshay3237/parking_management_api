package parking.management.dao;

import parking.management.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(Long userId);

    void deleteById(Long userId);

    void delete(User user);

    void update(User user);

    void save(User user);
    
    User findByUsername(String name);
}
