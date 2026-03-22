package com.nextgenz.service;

import com.nextgenz.repository.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsService implements UserDetailsService {

    private final StudentRepository studentRepo;

    public StudentDetailsService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return studentRepo.findByUsername(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "Student not found: " + username));
    }
}
