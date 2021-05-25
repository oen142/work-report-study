package biz.dreamaker.workreport.security.jwt;

import biz.dreamaker.workreport.security.AccountContext;
import biz.dreamaker.workreport.security.exception.InvalidJwtException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    @Value(value = "${jwt.sign-key}")
    private  String signingKey ;


    public AccountContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token)
            .orElseThrow(() -> new InvalidJwtException("유효한 토큰이 아닙니다."));

        String username = decodedJWT.getClaim("USERNAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();

        return new AccountContext(username, "passwordNulls", role);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(signingKey);
            JWTVerifier verifier = JWT.require(algorithm).build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}
