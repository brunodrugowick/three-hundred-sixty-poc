package dev.drugowick.threehundredsixty.controller.admin;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EmployeeAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * This rand is used to bypass a problem[1] with few tests functions.
     * These functions run individually, everything goes just fine;
     * on the other hand, if all class' method were run, the exception is throw again.
     *
     * [1]:
     *  org.springframework.dao.DataIntegrityViolationException
     */
    private Random rand = new Random();

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER"})
    void givenUserWithRoleUserOnly_whenGetList_thenBlock() throws Exception {
        mockMvc.perform(get("/admin/employees")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenGetList_thenOk() throws Exception {
        mockMvc.perform(get("/admin/employees")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenGetEmployeeById_thenOk() throws Exception {
        mockMvc.perform(get("/admin/employees/1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenPostEmployeeById_thenFail() throws Exception {
        mockMvc.perform(post("/admin/employees/1")
                .with(csrf())
                .param("email", "some.valid.email@email.com")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/employee-edit"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("employee", "name", "position", "roles")); // only fields with validator and failed
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenPostEmployeeById_thenOk() throws Exception {
        mockMvc.perform(post("/admin/employees/1")
                .with(csrf())
                .param("name", "John Foo")
                .param("position", "Analista de Sistemas")
                .param("email", "some.valid.email@email.com")
                .param("roles", "ROLE_USER,ROLE_ADMIN")
        )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/employees"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.model().errorCount(0));
    }// ¿are this test, the test-killer?

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenGetNewEmployeePage_thenOk() throws Exception {
        mockMvc.perform(get("/admin/employees/new")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenPostNewEmployeePage_thenOk() throws Exception {
        mockMvc.perform(post("/admin/employees/new")
                .with(csrf())
                .param("name", "John Foo" + rand.nextInt())
                .param("position", "Analista de Sistemas")
                .param("email", "some.valid.email." +  + rand.nextInt() +"@email.com")
                .param("roles", "ROLE_USER,ROLE_ADMIN")
        )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/employees"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.model().errorCount(0));
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenPostNewEmployeePage_thenFail() throws Exception {
        mockMvc.perform(post("/admin/employees/new")
                .with(csrf())
                .param("name", "John Foo")
                .param("position", "Analista de Sistemas")
                .param("email", "@email.com")
                .param("roles", "")
        )
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.view().name("admin/employee-edit"))
                .andExpect(MockMvcResultMatchers.model().errorCount(2))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("employee", "email", "roles"));
    }

    private void testToggle() throws Exception {
        mockMvc.perform(post("/admin/employees")
                .with(csrf())
                .param("id", "1")
        )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/employees"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.model().errorCount(0));
    }

    @Test
    @WithMockUser(username = "enrique.iglesias@email.com", password = "password", roles = {"USER", "ADMIN"})
    void givenUserWithRoleUserAdmin_whenPostToggleEmployee_thenOk() throws Exception {
        // called 2x to actually test toggle behaviour
        testToggle();
        testToggle();
    }
}