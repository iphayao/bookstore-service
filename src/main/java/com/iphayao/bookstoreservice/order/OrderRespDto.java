package com.iphayao.bookstoreservice.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRespDto {
    private double price;
}
