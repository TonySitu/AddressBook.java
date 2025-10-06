package lab4;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressBookTest {

    private AddressBook book;
    private BuddyInfo alice;
    private BuddyInfo bob;

    @Before
    public void setUp() {
        book = new AddressBook();
        alice = new BuddyInfo("Alice", "streets", "123");
        bob = new BuddyInfo("Bob",   "basement", "456");
        bob.setEmail("alice@bob.com");
    }

    @Test
    public void addBuddy() {
        assertEquals(0, book.getAddressSize());
        book.addBuddy(alice);
        assertEquals(1, book.getAddressSize());
        assertTrue(bookContains(alice));
    }

    @Test
    public void removeBuddy() {
        book.addBuddy(alice);
        book.addBuddy(bob);
        book.removeBuddy(alice);
        assertEquals("alice@bob.com", book.getBuddyList().get(0).getEmail());
        assertEquals(1, book.getAddressSize());
        assertFalse(bookContains(alice));
        assertTrue(bookContains(bob));
    }

    @Test
    public void getAddressSize() {
        assertEquals(0, book.getAddressSize());
        book.addBuddy(alice);
        assertEquals(1, book.getAddressSize());
        book.addBuddy(bob);
        assertEquals(2, book.getAddressSize());
        book.removeBuddy(alice);
        assertEquals(1, book.getAddressSize());
    }

    @Test
    public void run() {
        AddressBook.main(new String[0]);
    }

    private boolean bookContains(BuddyInfo buddy) {
        return book.containsBuddy(buddy);
    }
}