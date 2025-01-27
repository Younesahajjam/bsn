package com.koki.book.security;

import com.koki.book.user.User;
import com.koki.book.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        return repository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
