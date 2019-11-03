package com.iphayao.bookstoreservice.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    BookRespDto getAllBooks() {
        return BookRespDto.builder()
                .books(bookService.getAllBooks())
                .build();
    }
}
