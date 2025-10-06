package lab4;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookHttpTest {

    @LocalServerPort private int port;
    @Autowired private TestRestTemplate rest;

    /* ---------- helpers ---------- */
    private String url(String uri) { return "http://localhost:" + port + uri; }

    /* ---------- tests ------------ */
    @Test
    void createBookAndAddBuddy_roundTrip() {
        // 1. POST /book
        ResponseEntity<AddressBook> bookResp =
                rest.postForEntity(url("/book"), null, AddressBook.class);
        assertThat(bookResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long bookId = bookResp.getBody().getId();

        BuddyInfo buddy = new BuddyInfo("Linus", "Helsinki", "123");
        ResponseEntity<BuddyInfo> buddyResp =
                rest.postForEntity(url("/book/" + bookId + "/buddy"), buddy, BuddyInfo.class);
        assertThat(buddyResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(buddyResp.getBody().getName()).isEqualTo("Linus");

        ResponseEntity<BuddyInfo[]> listResp =
                rest.getForEntity(url("/book/" + bookId + "/buddy"), BuddyInfo[].class);
        assertThat(listResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listResp.getBody()).hasSize(1);
    }
}