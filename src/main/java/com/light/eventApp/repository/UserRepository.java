package com.light.eventApp.repository;

import com.light.eventApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email =:email")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.id=:id")
    Optional<User> getUserById(Long id);

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query("delete from User u where u.id=:id")
    Long delete(@Param("id") Long id);
}
