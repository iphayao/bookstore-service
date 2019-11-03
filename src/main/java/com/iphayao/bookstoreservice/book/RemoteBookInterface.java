package com.iphayao.bookstoreservice.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "RemoteBookRepository", url = "https://scb-test-book-publisher.herokuapp.com/books")
public interface RemoteBookInterface {
    @GetMapping()
    List<Book> getAllBooks();
}
