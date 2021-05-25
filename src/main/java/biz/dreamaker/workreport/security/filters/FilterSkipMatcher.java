package biz.dreamaker.workreport.security.filters;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FilterSkipMatcher implements RequestMatcher {

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher processingMatcher;

    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
        this.orRequestMatcher = ofRequestMatcher(pathToSkip);
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    private OrRequestMatcher ofRequestMatcher(List<String> pathToSkip) {
        return new OrRequestMatcher(
            pathToSkip.stream().map(AntPathRequestMatcher::new).collect(
                Collectors.toList()));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !orRequestMatcher.matches(request) && processingMatcher.matches(request);
    }

}
