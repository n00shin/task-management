package ir.chica.task.service;


import ir.chica.task.dto.TaskDto;
import ir.chica.task.enums.TaskTypeEnum;
import ir.chica.task.exception.RecordNotFoundException;
import ir.chica.task.model.Task;
import ir.chica.task.repository.TaskRepository;
import ir.chica.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final UserService userService;


    @Transactional
    public void persist(String taskName) throws UsernameNotFoundException {
        var task = new Task( taskName );
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.findUserByUsername( username );

        task.setOwner( user );

        repository.save( task );
    }


    public List<Task> findTasksByOwner() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var tasks = repository.findTasksByOwner( username );

        return tasks;
    }

    public void saveTask(TaskDto taskDto) {
        repository.save( Task.fromDto( taskDto ) );
    }

    public List<Task> getAll() {
        return (List<Task>) repository.findAll();
    }

    public List<Task> getTaskByTaskType(TaskTypeEnum typeEnum) {
        return new ArrayList<>( repository.findTaskByTaskType( typeEnum ) );
    }

    public void updateName(Long id, @Valid TaskDto taskDto) throws RecordNotFoundException {
        var name = taskDto.name();
        if (repository.existsById( id )) {
            repository.updateName( id, name );
        } else {
            throw new RecordNotFoundException( id );
        }
    }

    public void updateTimeToDo(Long id) throws RecordNotFoundException {
        if (repository.existsById( id )) {
            repository.updateTimeToDo( id );
        } else {
            throw new RecordNotFoundException( id );
        }
    }

    public List<String> getUserByTaskId(Long taskId) {
        return new ArrayList<>( repository.findUserByTaskId( taskId ) );
    }

    public void updateDeleted(Long id) throws RecordNotFoundException {
        if (repository.existsById( id )) {
            repository.updateDeleted( id );
        } else {
            throw new RecordNotFoundException( id );
        }

    }

    public List<Task> getInProcess() {
        return new ArrayList<>( repository.findAllInProcess() );
    }


    public Optional<Task> getTaskByID(Long id) throws RecordNotFoundException {
        Optional<Task> taskOptional = repository.findById( id );
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            return Optional.of( task );
        } else {
            throw new RecordNotFoundException( id );
        }
    }


    public void updateUpdatedAt(Long id) throws RecordNotFoundException {
        if (repository.existsById( id )) {
            repository.updateUpdatedAt( id );
        } else {
            throw new RecordNotFoundException( id );
        }
    }

    public void deleteById(Long id) {
        repository.deleteById( id );
    }
}

