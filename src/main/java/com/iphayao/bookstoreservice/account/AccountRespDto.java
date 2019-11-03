package com.iphayao.bookstoreservice.account;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountRespDto {
    private String name;
    private String surname;
    private String dateOfBirth;
    private List<Integer> books;

}
