package com.iphayao.bookstoreservice.order;

import com.iphayao.bookstoreservice.account.Account;
import com.iphayao.bookstoreservice.account.AccountService;
import com.iphayao.bookstoreservice.account.exception.AccountNotFoundException;
import com.iphayao.bookstoreservice.book.Book;
import com.iphayao.bookstoreservice.book.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static com.iphayao.bookstoreservice.TestHelper.mockAccount;
import static com.iphayao.bookstoreservice.TestHelper.mockOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private BookService bookService;
    @Mock
    private AccountService accountService;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void test_create_order_expect_price_of_order() throws AccountNotFoundException {
        // arrange
        OrderDto orderDto = new OrderDto();
        orderDto.setOrders(Arrays.asList(1, 2));
        String username = "test_user";
        Account account = mockAccount().get();

        when(bookService.getBookById(anyInt())).thenReturn(mockBook());

        // act
        double price = orderService.createOrder(username, orderDto, account);

        // assert
        assertEquals(200.0, price);
    }

    @Test
    void test_create_order_expect_save_order() throws AccountNotFoundException {
        // arrange
        OrderDto orderDto = new OrderDto();
        orderDto.setOrders(Arrays.asList(1, 2));
        Account account = mockAccount().get();
        String username = "test_user";

        when(bookService.getBookById(anyInt())).thenReturn(mockBook());

        // act
        double price = orderService.createOrder(username, orderDto, account);

        // assert
        verify(orderRepository, times(1)).saveAll(any());
    }

    @Test
    void test_create_order_expect_saved_order_with_account_id() throws AccountNotFoundException {
        // arrange
        OrderDto orderDto = new OrderDto();
        orderDto.setOrders(Arrays.asList(1, 2));
        Account account = mockAccount().get();
        String username = "test_user";

        List<Order> savedOrder = new ArrayList<>();

        when(bookService.getBookById(anyInt())).thenReturn(mockBook());
        when(orderRepository.saveAll(any())).thenAnswer(e -> {
            List<Order> orders = e.getArgument(0);
            IntStream.range(0, orders.size()).forEach(i -> orders.get(i).setId(i + 1));
            savedOrder.addAll(e.getArgument(0));
            return e.getArgument(0);
        });

        // act
        orderService.createOrder(username, orderDto, account);

        // assert
        assertEquals(2, savedOrder.size());
        savedOrder.forEach(order -> {
            assertNotNull(order.getId());
            assertEquals(1, order.getAccountId());
            assertEquals(100.0, order.getPrice());
        });

        assertEquals(1, savedOrder.get(0).getBookId());
        assertEquals(2, savedOrder.get(1).getBookId());
    }

    @Test
    void test_delete_order_by_account_id_expect_delete_records() {
        // arrange
        int accountId = 1;

        when(orderRepository.findByAccountId(eq(accountId))).thenReturn(Collections.singletonList(mockOrder()));

        // act
        orderService.deleteOrderByAccountId(accountId);

        // assert
        verify(orderRepository, times(1)).deleteAll(any());
    }

    private Optional<Book> mockBook() {
        return Optional.of(
                Book.builder()
                        .price(100.0)
                        .build()
        );
    }


}