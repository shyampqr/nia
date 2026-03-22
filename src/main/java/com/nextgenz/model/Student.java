package com.nextgenz.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "students")
public class Student implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(length = 50)
    private String rollNumber;

    @Column(length = 10)
    private String classLevel;          // e.g. "9", "10"

    @Column(nullable = false, length = 20)
    private String role = "ROLE_STUDENT"; // ROLE_STUDENT or ROLE_ADMIN

    @Column(nullable = false)
    private boolean enabled = true;

    // ── Constructors ─────────────────────────────────────────
    public Student() {}

    public Student(String username, String password, String fullName,
                   String rollNumber, String classLevel, String role) {
        this.username   = username;
        this.password   = password;
        this.fullName   = fullName;
        this.rollNumber = rollNumber;
        this.classLevel = classLevel;
        this.role       = role;
    }

    // ── UserDetails implementation ───────────────────────────
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override public String  getPassword()                { return password; }
    @Override public String  getUsername()                { return username; }
    @Override public boolean isAccountNonExpired()        { return true; }
    @Override public boolean isAccountNonLocked()         { return true; }
    @Override public boolean isCredentialsNonExpired()    { return true; }
    @Override public boolean isEnabled()                  { return enabled; }

    // ── Getters & Setters ────────────────────────────────────
    public Long    getId()                          { return id; }
    public void    setId(Long id)                   { this.id = id; }
    public void    setUsername(String v)            { this.username = v; }
    public void    setPassword(String v)            { this.password = v; }
    public String  getFullName()                    { return fullName; }
    public void    setFullName(String v)            { this.fullName = v; }
    public String  getRollNumber()                  { return rollNumber; }
    public void    setRollNumber(String v)          { this.rollNumber = v; }
    public String  getClassLevel()                  { return classLevel; }
    public void    setClassLevel(String v)          { this.classLevel = v; }
    public String  getRole()                        { return role; }
    public void    setRole(String v)                { this.role = v; }
    public void    setEnabled(boolean v)            { this.enabled = v; }
}
