package ir.chica.task.repository;


import ir.chica.task.enums.TaskTypeEnum;
import ir.chica.task.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query(" From Task  where done is null or deletedAt is null ")
    List<Task> findAllInProcess();

    @Query(value = "select u.username from task t inner join app_user u on t.user_id=u.id where t.id= :id", nativeQuery = true)
    List<String> findUserByTaskId(@Param("id") Long taskId);

    @Query(value = "from Task where taskType= :taskType  ")
    List<Task> findTaskByTaskType(@Param("taskType") TaskTypeEnum taskType);

    @Query(value = "select * from task t inner join app_user u on t.user_id=u.id where u.username= :username", nativeQuery = true)
    List<Task> findTasksByOwner(@Param("username") String username);

    @Modifying
    @Query("update Task set deleted=false , deletedAt=current_timestamp  where id= :id")
    void updateDeleted(@Param("id") Long id);


    @Modifying
    @Query("update Task  set done=current_timestamp where id= :id")
    void updateTimeToDo(@Param("id") Long id);

    @Modifying
    @Query("update Task set updatedAt= current_timestamp where id=:id")
    void updateUpdatedAt(@Param("id") Long id);

    @Modifying
    @Query("update Task  set name= :name where id=:id")
    void updateName(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Query(value = "update Task task set task.deleted=true where task.id=:id")
    void deleteById(@Param("id") Long id);
}

