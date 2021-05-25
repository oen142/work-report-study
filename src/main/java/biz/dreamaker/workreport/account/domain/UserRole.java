package biz.dreamaker.workreport.account.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;

@Getter
public enum UserRole {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), SUPER("ROLE_SUPER");

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public static UserRole getRoleByName(String roleName) {
        return Arrays.stream(UserRole.values())
            .filter(r -> r.isCorrectName(roleName)).findFirst()
            .orElseThrow(() -> new NoSuchElementException("검색된 권한이 없습니다."));
    }

    private boolean isCorrectName(String roleName) {
        return roleName.equalsIgnoreCase(this.roleName);
    }
}
