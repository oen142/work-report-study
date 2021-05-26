package biz.dreamaker.workreport.account.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.dreamaker.workreport.common.domain.BasicEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String phoneNumber;

    private boolean deleted;

    @Builder
    private Account(Long id, String username, String name, String password,
                    UserRole userRole, String phoneNumber, boolean deleted) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.deleted = deleted;
    }

    public static Account ofUser(String username, String name, String password, String phoneNumber) {
        return Account.builder()
                .id(null)
                .username(username)
                .name(name)
                .password(password)
                .userRole(UserRole.USER)
                .phoneNumber(phoneNumber)
                .deleted(false)
                .build();
    }

    public static Account ofAdmin(String username, String name, String password, String phoneNumber) {
        return Account.builder()
                .id(null)
                .username(username)
                .name(name)
                .password(password)
                .userRole(UserRole.ADMIN)
                .phoneNumber(phoneNumber)
                .deleted(false)
                .build();
    }

    public static Account ofSuper(String username, String name, String password, String phoneNumber) {
        return Account.builder()
                .id(null)
                .username(username)
                .name(name)
                .password(password)
                .userRole(UserRole.SUPER)
                .phoneNumber(phoneNumber)
                .deleted(false)
                .build();
    }

    public void update(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

