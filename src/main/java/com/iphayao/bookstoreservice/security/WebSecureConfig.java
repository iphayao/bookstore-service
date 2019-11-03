package com.iphayao.bookstoreservice.security;

import com.iphayao.bookstoreservice.account.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Collections;

@EnableWebSecurity
public class WebSecureConfig extends WebSecurityConfigurerAdapter {
    private AccountRepository accountRepository;

    public WebSecureConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authenticationRequests -> authenticationRequests
                .antMatchers(HttpMethod.POST, "/login", "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/books").permitAll()
                .anyRequest().authenticated())
                .csrf().disable()
                .addFilterAt(accountAuthenticationFilter(), BasicAuthenticationFilter.class);
    }

    private AccountAuthenticationFilter accountAuthenticationFilter() {
        return new AccountAuthenticationFilter(providerManager());
    }

    private ProviderManager providerManager() {
        return new ProviderManager(Collections.singletonList(new AccountAuthenticationProvider(accountRepository, passwordEncoder())));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
