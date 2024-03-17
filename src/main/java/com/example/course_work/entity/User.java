package com.example.course_work.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64,nullable = false)
    private String password;
    @Column(length = 128,nullable = false,unique = true)
    private String email;

    @Column(length = 13,nullable = false,unique = true)
    private String phone_number;

    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )

    private Set<Role> roles= new HashSet<>();


    public User(String password, String email, String phone_number) {
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public User(Long id) {
        this.id = id;
    }
}