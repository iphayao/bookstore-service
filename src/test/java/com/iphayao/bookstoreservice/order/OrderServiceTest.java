package com.iphayao.bookstoreservice.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Test
    void test_create_order_expect_price_of_order() {
        // arrange
        OrderDto orderDto = new OrderDto();
        orderDto.setOrders(Arrays.asList(1, 2));

        // act
        double price = orderService.createOrder(orderDto);

        // assert
        assertEquals(0.0, price);
    }
}