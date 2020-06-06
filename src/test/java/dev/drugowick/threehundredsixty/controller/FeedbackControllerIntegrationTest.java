package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.service.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FeedbackControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    Long nonexistentFeedbackId = 9990009999L;
    Long existentFeedbackId = 1L;

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password")
    void whenGetNonExistentFeedback_thenFail() throws Exception {
        mockMvc.perform(get("/feedback/" + nonexistentFeedbackId))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password")
    void whenGetExistentFeedback_thenLoadPage() throws Exception {
        mockMvc.perform(get("/feedback/" + existentFeedbackId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}