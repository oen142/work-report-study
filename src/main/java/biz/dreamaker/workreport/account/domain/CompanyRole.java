package biz.dreamaker.workreport.account.domain;


import java.util.NoSuchElementException;
import java.util.stream.Stream;

public enum CompanyRole {

    PERSONAL("ROLE_PERSONAL"), GROUP("ROLE_GROUP");


    private String roleName;

    CompanyRole(String roleName) {
        this.roleName = roleName;
    }

    public CompanyRole findRoleName(String roleName) {
        return Stream.of(CompanyRole.values())
                .filter(r -> r.isCorrectRole(roleName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당하는 롤을 찾을 수 없습니다."));

    }

    private boolean isCorrectRole(String roleName) {
        return this.roleName.equalsIgnoreCase(roleName);
    }
}
