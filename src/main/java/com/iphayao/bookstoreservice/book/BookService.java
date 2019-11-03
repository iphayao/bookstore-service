package com.iphayao.bookstoreservice.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private RemoteBookInterface remoteBookInterface;
    private RemoteRecommendationInterface remoteRecommendationInterface;

    public BookService(RemoteBookInterface remoteBookInterface, RemoteRecommendationInterface remoteRecommendationInterface) {
        this.remoteBookInterface = remoteBookInterface;
        this.remoteRecommendationInterface = remoteRecommendationInterface;
    }

    public List<Book> getAllBooks() {
        List<Book> recommendBooks = remoteRecommendationInterface.getRecommentBooks();
        List<Integer> recommendBookIds = recommendBooks.stream().map(Book::getId).collect(Collectors.toList());

        List<Book> books = remoteBookInterface.getAllBooks().stream()
                .filter(book -> !recommendBookIds.contains(book.getId())).collect(Collectors.toList());

        recommendBooks.addAll(books);

        return recommendBooks;
    }

    public Optional<Book> getBookById(int id) {
        return remoteBookInterface.getAllBooks().stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }
}
