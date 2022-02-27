package ir.chica.task.repository;


import ir.chica.task.model.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query(value = "From Role  where id=:id and main=:main")
    Optional<Role> findByIdAndMain(@Param("id") Long id,
                                   @Param("main") String main);

    @Query(value = "From Role  where main=:main and category=:category")
    List<Role> findByMainAndCategory(@Param("main") String main,
                                     @Param("category") String category);

    @Query(value = "From Role  where roleName=:rolrName ")
    Role findByRoleName(@Param("roleName") String roleName);

//    List<Role> findByName(@Param("name") String name);


    @Modifying
    @Query(value = "update Role set main=:main where id=:id")
    void updateMain(@Param("id") Long id,
                    @Param("main") String main);

    @Modifying
    @Query(value = "update Role  set category=:category where id=:id")
    void updateCategory(@Param("id") Long id,
                        @Param("category") String category);

    @Modifying
    @Query(value = "update Role set roleName=:roleName where id=:id")
    void updateName(@Param("id") Long id,
                    @Param("name") String name);

    @Modifying
    @Query(value = "update Role  set main=:main ,category=:category,roleName=:roleName where id=:id")
    void updateRole(@Param("id") Long id,
                    @Param("main") String main,
                    @Param("category") String category,
                    @Param("name") String name);

}