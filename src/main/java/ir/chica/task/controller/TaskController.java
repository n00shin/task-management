package ir.chica.task.controller;


import ir.chica.task.dto.TaskDto;
import ir.chica.task.dto.TaskResponseDto;
import ir.chica.task.enums.TaskTypeEnum;
import ir.chica.task.exception.RecordNotFoundException;
import ir.chica.task.model.Task;
import ir.chica.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Validated
@PreAuthorize( "isAuthenticated()" )
public class TaskController {
    private final TaskService service;


//    @PostMapping
//    @Secured("ROLE_ADD_TASK")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void create(@RequestBody Map<String,Object> body) {
//        var taskName = body.get("name").toString();
//
//        if (taskName == null || taskName.isBlank()) {
//            // todo throw exception
//            return;
//        }
//
//        service.persist(taskName);
//    }

    @PostMapping("/newTask")
    @Secured("ROLE_ADD_TASK")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewTask(TaskDto taskDto) {
        ModelAndView modelAndView = new ModelAndView();
        service.saveTask( taskDto );
        modelAndView.addObject("successMessage", "Task has been created successfully");
        modelAndView.addObject("task", new Task());
        modelAndView.setViewName("taskboard");
        return "redirect:/taskboard";
    }


    @GetMapping("/{type}")
    @Secured("ROLE_ADMIN")
    @Validated
    public List<TaskTypeEnum> getTaskByTasktype
            (@PathVariable TaskTypeEnum typeEnum) {
        return service.getTaskByTaskType( typeEnum ).stream().map(Task::getTaskType).collect( Collectors.toList());
    }
/*
    @RequestMapping
    public ModelAndView findAllTasks(Model model) {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        model.addAttribute("tasks",tasks);
        return new ModelAndView("taskboard", "tasks", tasks);
    }
 */

    @PostMapping
    public void saveTask(@Valid @RequestBody TaskDto newTask) {
        service.saveTask( newTask );
    }

    @GetMapping
    @Secured( "ROLE_ADMIN" )
    public List<Task> getAll() {
        return service.getAll();
    }


    @GetMapping
    @Secured("Role_ADMIN")
    public List<TaskResponseDto> getTaskByUsername(){
        return service.findTasksByOwner().stream().map( TaskResponseDto::new).collect( Collectors.toList());
    }

    @GetMapping("/{taskId}")
    @Validated
    public List<String> getUserByTaskId( @PathVariable Long taskId) {
        return service.getUserByTaskId( taskId );
    }

    @PutMapping("/update/name/{id}")
    @Validated
    public void updateName( @PathVariable Long id, @Valid @RequestBody TaskDto taskDto) throws RecordNotFoundException {
        service.updateName( id, taskDto );
    }

    @GetMapping("/{id}")
    @Secured( "ROLE_ADMIN" )
    public Optional<Task> getTaskByID(@PathVariable Long id) throws RecordNotFoundException {
        return service.getTaskByID( id );
    }

    @PutMapping("/update/{id}")
    @Validated
    public void updateUpdatedAt( @PathVariable Long id) throws RecordNotFoundException {
        service.updateUpdatedAt( id );
    }

    @PutMapping("/update/done/{id}")
    @Validated
    public void updateTimeToDo( @PathVariable Long id) throws RecordNotFoundException {
        service.updateTimeToDo( id );
    }

    @DeleteMapping("/{id}")
    @Validated
    void deleteById( @PathVariable Long id) {
        service.deleteById( id );
    }

    @PutMapping("/update/deleted/{id}")
    @Validated
    public void updateDeleted( @PathVariable Long id) throws RecordNotFoundException {
        service.updateDeleted( id );
    }
}





