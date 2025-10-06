package lab4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JPATest {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    @Transactional
    public void persistAddressBookWithBuddies() {
        // Create an AddressBook and add buddies to it
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Bob", "Builder St", "456-7890"));
        book.addBuddy(new BuddyInfo("Carol", "Singer Ave", "789-0123"));

        // Save the AddressBook (buddies are saved via CascadeType.ALL)
        addressBookRepository.save(book);

        // Retrieve the saved AddressBook
        AddressBook loadedBook = addressBookRepository.findById(book.getId()).orElse(null);

        // Assert that the book was found and contains the correct number of buddies
        Assertions.assertNotNull(loadedBook);

        // Verify that one of the buddies is "Carol"
        Assertions.assertTrue(loadedBook.getBuddyList().stream()
                .anyMatch(b -> b.getName().equals("Carol")));
    }
}