package com.iphayao.bookstoreservice.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private List<Integer> orders;
}
