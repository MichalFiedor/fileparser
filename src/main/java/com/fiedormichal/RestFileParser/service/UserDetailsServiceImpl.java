package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.model.Authority;
import com.fiedormichal.RestFileParser.model.User;
import com.fiedormichal.RestFileParser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority : user.getAuthorities()){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
