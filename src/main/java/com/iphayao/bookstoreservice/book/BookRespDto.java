package com.iphayao.bookstoreservice.book;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookRespDto {
    List<Book> books;
}
