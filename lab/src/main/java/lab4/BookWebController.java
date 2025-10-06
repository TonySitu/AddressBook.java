package lab4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookWebController {

    private final AddressBookRepository bookRepo;

    public BookWebController(AddressBookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/book/{id}/buddy-list")
    public String buddyList(@PathVariable Long id, Model model) {
        AddressBook book = bookRepo.findById(id)
                .orElseThrow(() -> new AddressBookNotFoundException(id));
        model.addAttribute("book", book);
        return "buddy-list";
    }
}