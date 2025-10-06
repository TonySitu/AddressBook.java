package lab4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookMockMvcTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @Test
    void createBookAndAddBuddy_mockMvc() throws Exception {
        // 1. POST /book
        String bookJson = mvc.perform(post("/book"))
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long bookId = (Long) mapper.readTree(bookJson).get("id").asLong();
        BuddyInfo buddy = new BuddyInfo("Ada", "London", "42");
        mvc.perform(post("/book/" + bookId + "/buddy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(buddy)))
                .andExpect(jsonPath("$.name").value("Ada"));

        mvc.perform(get("/book/" + bookId + "/buddy"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Ada"));
    }
}