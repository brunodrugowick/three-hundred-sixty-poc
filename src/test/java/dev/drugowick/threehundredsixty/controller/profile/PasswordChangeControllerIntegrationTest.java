package dev.drugowick.threehundredsixty.controller.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drugowick.threehundredsixty.dto.PasswordChange;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PasswordChangeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetTemplatePasswordChange_thenReturns200() throws Exception {
        mockMvc.perform(get("/profile/password"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password")
    void whenPostTemplatePasswordChange_thenReturnsRedirection() throws Exception {
        PasswordChange passwordChange = new PasswordChange();
        passwordChange.setCurrentPassword("password");
        passwordChange.setNewPassword("new-valid-password");
        passwordChange.setNewPasswordConfirmation("new-valid-password");

        mockMvc.perform(
                post("/profile/password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("currentPassword", "password")
                        .param("newPassword", "new-valid-password")
                        .param("newPasswordConfirmation", "new-valid-password")
        ).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(forwardedUrl(null));
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password")
    void whenPostTemplatePasswordChange_thenReturnsWrongCurrentPassword() throws Exception {
        mockMvc.perform(
                post("/profile/password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("currentPassword", "wrong-initial-password")
                        .param("newPassword", "new-valid-password")
                        .param("newPasswordConfirmation", "new-valid-password")
        ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profile/password-change"))
                .andExpect(forwardedUrl(null));
    }
}
