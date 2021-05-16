package com.fiedormichal.RestFileParser.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private Integer enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority")
    private List<Authority> authorities;

}
