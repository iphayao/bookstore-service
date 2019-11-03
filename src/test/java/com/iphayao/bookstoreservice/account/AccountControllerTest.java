package com.iphayao.bookstoreservice.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.iphayao.bookstoreservice.TestHelper.mockAccount;
import static com.iphayao.bookstoreservice.TestHelper.mockAccountDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    @SpyBean
    public AccountMapperImpl accountMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test_create_user_expect_response_ok() throws Exception {
        // arrange
        AccountDto mockAccountDto = mockAccountDto();

        // act
        // assert
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockAccountDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void test_get_user_expect_user_info() throws Exception {
        // arrange
        Account account = mockAccount().get();
        when(accountService.getUserByUsername(eq("test"))).thenReturn(account);

        // act
        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertNotNull(result.getResponse().getContentAsString());

        AccountRespDto actualAccount = objectMapper.readValue(result.getResponse().getContentAsString(), AccountRespDto.class);
        assertEquals(account.getName(), actualAccount.getName());
        assertEquals(account.getSurname(), actualAccount.getSurname());
        //assertEquals(account.getDateOfBirth(), actualAccount.getDateOfBirth());
    }

    @Test
    @WithMockUser(username = "test")
    void test_delete_user_expect_response_ok() throws Exception {
        // arrange
        // act
        // asset
        mockMvc.perform(delete("/users"))
                .andExpect(status().isOk());
    }

}