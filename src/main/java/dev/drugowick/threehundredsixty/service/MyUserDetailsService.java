package dev.drugowick.threehundredsixty.service;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.entity.security.MyUserDetails;
import dev.drugowick.threehundredsixty.domain.repository.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = userRepository.findByEmail(s);

        optionalEmployee.orElseThrow(() -> new UsernameNotFoundException("Not found: " + s));

        return optionalEmployee.map(MyUserDetails::new).get();
    }
}
