package ir.chica.task.repository;


import ir.chica.task.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "From User where username = :username and deleted = false ")
    Optional<User> findByUsernameAndDeletedIsFalse(@Param("username") String username);

    @Query(value = "From User where email = :email ")
    User findUserByEmail(@Param("email") String email);


    @Modifying
    @Query(value = "update User set password= :password where id=:id")
    void updatePassword(@Param("id") Long id,
                        @Param("password") String password);

    @Modifying
    @Query(value = "update User set email= :email where id=:id")
    void updateEmail(@Param("id") Long id,
                     @Param("email") String email);

    @Modifying
    @Query(value = "update User set deleted=true where id=:id")
    void deleteById(@Param("id") Long id);

    @Modifying
    @Query(value = "update User  set deleted=true where id IN :id")
    void deleteByIds(@Param("id") List<Long> ids);


    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


}

