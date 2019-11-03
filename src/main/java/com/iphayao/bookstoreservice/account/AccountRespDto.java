package com.iphayao.bookstoreservice.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRespDto {
    private String name;
    private String surname;
    private String dateOfBirth;
    private List<Integer> books;

}
