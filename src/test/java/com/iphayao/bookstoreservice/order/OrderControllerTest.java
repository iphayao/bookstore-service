package com.iphayao.bookstoreservice.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iphayao.bookstoreservice.account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.iphayao.bookstoreservice.TestHelper.mockOrderDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "test")
    void test_create_order_expect_total_price() throws Exception {
        // arrange
        OrderDto orderDto = mockOrderDto();
        when(orderService.createOrder(anyString(), any())).thenReturn(999.99);

        // act
        MvcResult result = mockMvc.perform(post("/users/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertNotNull(result.getResponse().getContentAsString());

        OrderRespDto respDto = objectMapper.readValue(result.getResponse().getContentAsString(), OrderRespDto.class);
        assertEquals(999.99, respDto.getPrice());
    }

}