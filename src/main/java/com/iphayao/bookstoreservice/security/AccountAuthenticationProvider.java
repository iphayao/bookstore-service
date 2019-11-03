package com.iphayao.bookstoreservice.security;

import com.iphayao.bookstoreservice.account.Account;
import com.iphayao.bookstoreservice.account.AccountRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

class AccountAuthenticationProvider implements AuthenticationProvider {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public AccountAuthenticationProvider(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<Account> account = accountRepository.findByUsername(authentication.getName());
        final UserDetails[] userDetails = new UserDetails[1];

        if (!account.isPresent()) {
            return null;
        }

        account.ifPresent(a -> {
            if (passwordEncoder.matches((CharSequence) authentication.getCredentials(), a.getPassword())) {
                userDetails[0] = loadUserByUsername(authentication);
            } else {
                throw new BadCredentialsException("Incorrect Password");
            }
        });

        return createSuccessAuthentication(authentication.getName(), authentication, userDetails[0]);
    }

    private UserDetails loadUserByUsername(Authentication authentication) {
        return new User(authentication.getName(), (String) authentication.getCredentials(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private Authentication createSuccessAuthentication(String name, Authentication authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(name, authentication.getCredentials(),
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(user);
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
