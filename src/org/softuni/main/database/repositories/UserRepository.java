package database.repositories;

import database.models.User;

import java.util.List;

public class UserRepository extends BaseRepository {
    private boolean create(String username, String password) {
        this.entityManager.persist(new User(username, password));
        return true;
    }

    private User[] findAll() {
        List<User> resultList = this.entityManager
                .createNativeQuery("SELECT * FROM users", User.class)
                .getResultList();

        return resultList.toArray(new User[resultList.size()]);
    }
}
