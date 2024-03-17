package com.example.course_work.repository;

import com.example.course_work.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  JpaRepository<User,Long>, PagingAndSortingRepository<User,Long>{
    @Query("SELECT u FROM User u WHERE u.email= :email")
    public User getUserByEmail(@Param("email")String email);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ',u.phone_number, ' ') LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
    public Long countById(Long id);

    @Query("UPDATE User u SET u.enabled=true WHERE u.id=?1")
    @Modifying
    public void enabled(Long id);
    @Query("SELECT u FROM User u WHERE u.phone_number= :phone_number")
    User getUserByPhoneNumber(String phone_number);

    public User findUserByEmail(String email);

}
