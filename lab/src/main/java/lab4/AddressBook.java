package lab4;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BuddyInfo> buddyList = new ArrayList<>();

    public AddressBook() {}

    public void addBuddy(BuddyInfo buddy) {
        buddyList.add(buddy);
        buddy.setAddressBook(this);
    }

    public void removeBuddy(BuddyInfo buddy) {
        buddyList.remove(buddy);
        buddy.setAddressBook(null);
    }

    public int getAddressSize() {
        return buddyList.size();
    }

    public List<BuddyInfo> getBuddyList() {
        return buddyList;
    }

    public boolean containsBuddy(BuddyInfo buddy) {
        return buddyList.contains(buddy);
    }

    public Long getId() {
        return this.id;
    }

    public static void main(String[] args) {
        BuddyInfo tony = new BuddyInfo("Tony", "Carleton", "613");
        BuddyInfo ton = new BuddyInfo("Ton", "Carleton", "416");

        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(tony);
        addressBook.addBuddy(ton);
    }
}
