package com.iphayao.bookstoreservice.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private RemoteBookInterface remoteBookInterface;

    public BookController(RemoteBookInterface remoteBookInterface) {
        this.remoteBookInterface = remoteBookInterface;
    }

    @GetMapping
    List<Book> getAllBooks() {
        return remoteBookInterface.getAllBooks();
    }
}
