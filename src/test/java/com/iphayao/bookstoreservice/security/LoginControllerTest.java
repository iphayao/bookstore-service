package com.iphayao.bookstoreservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iphayao.bookstoreservice.account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.iphayao.bookstoreservice.TestHelper.mockAccount;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test_login_expect_response_ok() throws Exception {
        // arrange
        Map<String, String> authBody = new HashMap<>();
        authBody.put("username", "test");
        authBody.put("password", "P@ssw0rd");

        when(accountRepository.findByUsername(authBody.get("username"))).thenReturn(mockAccount(authBody));

        // act
        // assert
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authBody)))
                .andExpect(status().isOk());
    }

    @Test
    void test_login_expect_response_unauthorized() throws Exception {
        // arrange
        Map<String, String> authBody = new HashMap<>();
        authBody.put("username_fake", "test");
        authBody.put("password_fake", "P@ssw0rd");

        // act
        // assert
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authBody)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_login_expect_response_unauthorized_when_user_not_register() throws Exception {
        // arrange
        Map<String, String> authBody = new HashMap<>();
        authBody.put("username", "test");
        authBody.put("password", "P@ssw0rd");

        when(accountRepository.findByUsername(authBody.get("username"))).thenReturn(Optional.empty());

        // act
        // assert
        mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authBody)))
                .andExpect(status().isUnauthorized());
    }
}