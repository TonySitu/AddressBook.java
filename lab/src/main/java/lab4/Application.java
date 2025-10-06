package lab4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepository, BuddyRepository buddyRepository) {
        return (args) -> {
            AddressBook book = new AddressBook();
            addressBookRepository.save(book);
            buddyRepository.save(new BuddyInfo("Jack", "Bauer", "47832910"));
            buddyRepository.save(new BuddyInfo("Chloe", "O'Brian", "47832910"));
            buddyRepository.save(new BuddyInfo("Kim", "Bauer", "47832910"));
            buddyRepository.save(new BuddyInfo("David", "Palmer", "47832910"));
            buddyRepository.save(new BuddyInfo("Michelle", "Kessler" , "47832910"));
        };
    }
}
