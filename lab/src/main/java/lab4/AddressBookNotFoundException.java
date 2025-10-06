package lab4;

public class AddressBookNotFoundException extends RuntimeException {
    AddressBookNotFoundException(Long id) {
        super("Could not find BuddyInfo " + id);
    }
}
