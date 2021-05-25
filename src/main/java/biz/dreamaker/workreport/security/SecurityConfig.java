package biz.dreamaker.workreport.security;

import biz.dreamaker.workreport.security.filters.FilterSkipMatcher;
import biz.dreamaker.workreport.security.filters.FormLoginFilter;
import biz.dreamaker.workreport.security.filters.JwtAuthenticationFilter;
import biz.dreamaker.workreport.security.handlers.FormLoginAuthenticationSuccessHandler;
import biz.dreamaker.workreport.security.handlers.JwtAuthenticationFailureHandler;
import biz.dreamaker.workreport.security.jwt.HeaderTokenExtractor;
import biz.dreamaker.workreport.security.providers.FormLoginAuthenticationProvider;
import biz.dreamaker.workreport.security.providers.JwtAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;

    @Autowired
    private FormLoginAuthenticationProvider formLoginProvider;

    @Autowired
    private JwtAuthenticationProvider jwtProvider;

    @Autowired
    private JwtAuthenticationFailureHandler jwtFailureHandler;

    @Autowired
    private HeaderTokenExtractor headerTokenExtractor;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    protected FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter("/formlogin",
            formLoginAuthenticationSuccessHandler, null,
            objectMapper);

        formLoginFilter.setAuthenticationManager(super.authenticationManagerBean());

        return formLoginFilter;
    }

    protected JwtAuthenticationFilter jwtFilter() throws Exception {

        FilterSkipMatcher matcher = new FilterSkipMatcher(
            Arrays.asList("/formlogin", "/social" , "/enroll/admin/**"), "/api/**");

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher,
            jwtFailureHandler, headerTokenExtractor);

        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(this.formLoginProvider)
            .authenticationProvider(this.jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
            .csrf().disable();

        http
            .headers().frameOptions().disable();

        http
            .authorizeRequests()
            .antMatchers("/h2-console**").permitAll();

        http
            .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
