package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.service.MyUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FeedbackControllerUnitTest {
    @Mock
    private Model model;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private FeedbackController feedbackController;

    Long nonexistentFeedbackId = 9990009999L;
    Long existentFeedbackId = 1L;

    @Test
    void whenNonExistentFeedback_thenThrowsException() {
        UsernamePasswordAuthenticationToken principal = this.getPrincipal("enrique.iglesias@email.com");
        Assertions.assertThrows(RuntimeException.class, () -> {
            feedbackController.getFeedbacks(principal, model, nonexistentFeedbackId);
        });
    }

    @Test
    void whenExistentFeedback_thenThrowsException() {
        UsernamePasswordAuthenticationToken principal = this.getPrincipal("enrique.iglesias@email.com");
        Assertions.assertEquals(feedbackController.feedbackListQuestionTemplate,
                feedbackController.getFeedbacks(principal, model, existentFeedbackId));
    }

    protected UsernamePasswordAuthenticationToken getPrincipal(String username) {
        // src from: https://stackoverflow.com/q/15203485/7362660
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities());
    }
}