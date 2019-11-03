package com.iphayao.bookstoreservice.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private RemoteBookInterface remoteBookInterface;
    @InjectMocks
    private BookService bookService;

    @Test
    void test_get_all_book_expect_list_of_books() {
        // arrange
        when(remoteBookInterface.getAllBooks()).thenReturn(mockRemoteBook());

        // act
        List<Book> books = bookService.getAllBooks();

        // assert
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    void test_get_book_by_id_expect_book() {
        // arrange
        when(remoteBookInterface.getAllBooks()).thenReturn(mockRemoteBook());

        // act
        Optional<Book> book = bookService.getBookById(1);

        // assert
        assertTrue(book.isPresent());
        assertEquals(1, book.get().getId());
    }

    @Test
    void test_get_book_by_id_expect_book_info() {
        // arrange
        when(remoteBookInterface.getAllBooks()).thenReturn(mockRemoteBook());

        // act
        Optional<Book> book = bookService.getBookById(1);

        // assert
        book.ifPresent(b -> {
            assertEquals("test book name", b.getBookName());
            assertEquals("john doe", b.getAuthorName());
            assertEquals(100.0, b.getPrice());
        });
    }

    private List<Book> mockRemoteBook() {

        return Arrays.asList(
                Book.builder()
                        .id(1)
                        .bookName("test book name")
                        .authorName("john doe")
                        .price(100.0)
                        .build(),
                Book.builder()
                        .id(2)
                        .bookName("test book name")
                        .authorName("john doe")
                        .price(100.0)
                        .build()
        );
    }
}