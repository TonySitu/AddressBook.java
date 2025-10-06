package lab4;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {

    /* Spring-Data parses the method name */
    Optional<BuddyInfo> findBuddyByIdAndId(Long buddyId, Long bookId);
}
