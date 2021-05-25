package biz.dreamaker.workreport.security.jwt;

import biz.dreamaker.workreport.security.AccountContext;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    @Value(value = "${jwt.sign-key}")
    private String signingKey;

    public String generateToken(AccountContext context) {

        String token = null;

        try {
            token = JWT.create()
                .withIssuer("dreamaker")
                .withClaim("USERNAME", context.getAccount().getUsername())
                .withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
                .sign(generateAlgorithm());

        } catch (Exception e) {
            log.error("error : " + e.getMessage());
        }

        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(signingKey);
    }

}
