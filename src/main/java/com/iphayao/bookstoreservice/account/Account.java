package com.iphayao.bookstoreservice.account;

import com.iphayao.bookstoreservice.order.Order;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Date dateOfBirth;
    @OneToMany(mappedBy = "accountId")
    private List<Order> orders;
}
