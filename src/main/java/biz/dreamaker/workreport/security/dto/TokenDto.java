package biz.dreamaker.workreport.security.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenDto {

    private String token;
    private Collection<GrantedAuthority> authorities;

}
