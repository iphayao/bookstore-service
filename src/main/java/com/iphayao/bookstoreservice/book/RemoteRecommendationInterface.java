package com.iphayao.bookstoreservice.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "RemoteRecommendation", url = "https://scb-test-book-publisher.herokuapp.com/books/recommendation")
public interface RemoteRecommendationInterface {

    @GetMapping
    public List<Book> getRecommentBooks();
}
