package biz.dreamaker.workreport.security.jwt;

import biz.dreamaker.workreport.security.exception.InvalidJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class HeaderTokenExtractor {

    public static final String HEADER_PREFIX = "Bearer ";

    public String extract(String header) {
        if (StringUtils.isEmpty(header) || header.length() < HEADER_PREFIX.length()) {
            throw new InvalidJwtException("올바른 토큰 정보가 아닙니다.");
        }

        return header.substring(HEADER_PREFIX.length());
    }
}

