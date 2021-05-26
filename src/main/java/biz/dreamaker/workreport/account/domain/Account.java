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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String phoneNumber;

    private boolean deleted;

    @Builder
    private Account(Long id, String username, String password,
                    UserRole userRole, String phoneNumber, boolean deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.deleted = deleted;
    }

    public static Account ofUser(String username, String password, String phoneNumber) {
        return Account.builder()
                .id(null)
                .username(username)
                .password(password)
                .userRole(UserRole.USER)
                .phoneNumber(phoneNumber)
                .deleted(false)
                .build();
    }

    public static Account ofAdmin(String username, String password, String phoneNumber) {
        return Account.builder()
                .id(null)
                .username(username)
                .password(password)
                .userRole(UserRole.ADMIN)
                .phoneNumber(phoneNumber)
                .deleted(false)
                .build();
    }

    public static Account ofSuper(String username, String password, String phoneNumber) {
        return Account.builder()
                .id(null)
                .username(username)
                .password(password)
                .userRole(UserRole.SUPER)
                .phoneNumber(phoneNumber)
                .deleted(false)
                .build();
    }

    public void update(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }
}

