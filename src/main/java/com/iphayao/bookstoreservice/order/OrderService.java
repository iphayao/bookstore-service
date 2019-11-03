package com.iphayao.bookstoreservice.order;

import com.iphayao.bookstoreservice.account.Account;
import com.iphayao.bookstoreservice.account.AccountService;
import com.iphayao.bookstoreservice.account.exception.AccountNotFoundException;
import com.iphayao.bookstoreservice.book.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private BookService bookService;
    private OrderRepository orderRepository;

    public OrderService(BookService bookService, OrderRepository orderRepository) {
        this.bookService = bookService;
        this.orderRepository = orderRepository;
    }

    public double createOrder(String username, OrderDto orderDto, Account account) throws AccountNotFoundException {
        List<Order> orders = new ArrayList<>();
        orderDto.getOrders()
                .forEach(bookId -> bookService.getBookById(bookId)
                .ifPresent(b -> orders.add(Order.builder()
                        .accountId(account.getId())
                        .bookId(bookId)
                        .price(b.getPrice())
                        .build()
                )));

        orderRepository.saveAll(orders);

        return orders.stream().mapToDouble(Order::getPrice).sum();
    }

    public void deleteOrderByAccountId(int accountId) {
        List<Order> orders = orderRepository.findByAccountId(accountId);
        orderRepository.deleteAll(orders);
    }
}
