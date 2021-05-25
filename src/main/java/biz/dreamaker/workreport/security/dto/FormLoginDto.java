package biz.dreamaker.workreport.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FormLoginDto {

    private String userId;
    private String password;
}
