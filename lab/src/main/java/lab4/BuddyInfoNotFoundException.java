package lab4;

public class BuddyInfoNotFoundException extends RuntimeException {
    BuddyInfoNotFoundException(Long id) {
        super("Could not find BuddyInfo " + id);
    }
}
