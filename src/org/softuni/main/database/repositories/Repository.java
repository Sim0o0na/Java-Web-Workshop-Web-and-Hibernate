package database.repositories;

public interface Repository {
    Object doAction(String action, Object... args);

    void dismiss();
}
