package com.example.course_work.security;


import com.example.course_work.entity.User;
import com.example.course_work.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

      User user =  userRepo.getUserByEmail(email);

      if(user != null){
          return new MyUserDetails(user);
      }
      throw new UsernameNotFoundException("Мы не можем найти пользователя с таким адресом почты" + email);
    }
}
