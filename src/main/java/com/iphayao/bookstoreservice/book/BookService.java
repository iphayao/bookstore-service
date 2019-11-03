package com.iphayao.bookstoreservice.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private RemoteBookInterface remoteBookInterface;

    public List<Book> getAllBooks() {
        return remoteBookInterface.getAllBooks();
    }

    public Optional<Book> getBookById(int id) {
        return remoteBookInterface.getAllBooks().stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }
}
