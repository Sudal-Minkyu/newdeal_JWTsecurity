package com.broadwave.security.account;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="bs_team_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(unique = true,name="user_name")
    private String username;

    @Column(name="user_password")
    private String password;

    @Column(name="user_role")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name="insert_date")
    private String insertDate;

}
