package lab4;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/book")
public class Controller {

    private final AddressBookRepository bookRepo;
    private final BuddyRepository buddyRepo;
    public Controller(AddressBookRepository b, BuddyRepository p) {
        this.bookRepo = b;
        this.buddyRepo = p;
    }

    /* ========================= ADDRESS-BOOK ========================= */
    @GetMapping
    public List<AddressBook> allBooks() {
        return (List<AddressBook>) bookRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<AddressBook> createBook(@RequestBody(required = false) AddressBook book) {
        if (book == null) {
            book = new AddressBook();
        }

        AddressBook saved = bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public AddressBook getBook(@PathVariable Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new AddressBookNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        if (!bookRepo.existsById(id)) throw new AddressBookNotFoundException(id);
        bookRepo.deleteById(id);
    }

    /* ================= BUDDIES **INSIDE** A BOOK ==================== */

    @GetMapping("/{bookId}/buddy")
    public List<BuddyInfo> getBuddiesInBook(@PathVariable Long bookId) {
        AddressBook book = bookRepo.findById(bookId)
                .orElseThrow(() -> new AddressBookNotFoundException(bookId));
        return book.getBuddyList();
    }

    @PostMapping("/{bookId}/buddy")
    public ResponseEntity<BuddyInfo> addBuddyToBook(@PathVariable Long bookId,
                                                    @RequestBody BuddyInfo buddy) {
        AddressBook book = bookRepo.findById(bookId)
                .orElseThrow(() -> new AddressBookNotFoundException(bookId));
        book.addBuddy(buddy);
        bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(buddy);
    }

    @GetMapping("/{bookId}/buddy/{buddyId}")
    public BuddyInfo getBuddyInBook(@PathVariable Long bookId,
                                    @PathVariable Long buddyId) {
        if (!bookRepo.existsById(bookId))
            throw new AddressBookNotFoundException(bookId);
        return bookRepo.findBuddyByIdAndId(buddyId, bookId)
                .orElseThrow(() -> new BuddyInfoNotFoundException(buddyId));
    }

    @DeleteMapping("/{bookId}/buddy/{buddyId}")
    public void removeBuddyFromBook(@PathVariable Long bookId,
                                    @PathVariable Long buddyId) {

        AddressBook book = bookRepo.findById(bookId).orElseThrow(() -> new AddressBookNotFoundException(bookId));

        BuddyInfo buddy = book.getBuddyList()
                .stream()
                .filter(b -> b.getId().equals(buddyId))
                .findFirst()
                .orElseThrow(() -> new BuddyInfoNotFoundException(buddyId));

        book.removeBuddy(buddy);
        bookRepo.save(book);
    }

    /* =================== STAND-ALONE BUDDY ENDPOINTS ================= */

    @GetMapping("/buddy")
    public List<BuddyInfo> allBuddies() {
        return (List<BuddyInfo>) buddyRepo.findAll();
    }

    @GetMapping("/buddy/{id}")
    public BuddyInfo oneBuddy(@PathVariable Long id) {
        return buddyRepo.findById(id)
                .orElseThrow(() -> new BuddyInfoNotFoundException(id));
    }

    @PutMapping("/buddy/{id}")
    public BuddyInfo replaceBuddy(@RequestBody BuddyInfo newBuddy,
                                  @PathVariable Long id) {
        return buddyRepo.findById(id)
                .map(b -> {
                    b.setName(newBuddy.getName());
                    b.setAddress(newBuddy.getAddress());
                    b.setPhoneNumber(newBuddy.getPhoneNumber());
                    return buddyRepo.save(b);
                })
                .orElseThrow(() -> new BuddyInfoNotFoundException(id));
    }

    @DeleteMapping("/buddy/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBuddy(@PathVariable Long id) {
        if (!buddyRepo.existsById(id)) throw new BuddyInfoNotFoundException(id);
        buddyRepo.deleteById(id);
    }
}