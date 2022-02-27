package ir.chica.task.controller;

import ir.chica.task.dto.RoleDto;
import ir.chica.task.exception.RecordNotFoundException;
import ir.chica.task.model.Role;
import ir.chica.task.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {

    private  final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public void saveRole(@RequestBody RoleDto roleDto){
        var main=roleDto.main();
        var category=roleDto.category();
        var name=roleDto.roleName();
        roleService.saveRole(main,category,name);
    }
    @GetMapping
    @ResponseBody
    public List<Role> getAll(){
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Role> getRole(@PathVariable Long id) throws RecordNotFoundException {
        if (roleService.getRole(id).isEmpty()){
            throw new RecordNotFoundException(id);
        }
        return roleService.getRole(id);
    }
    @GetMapping("/{id}/{main}")
    @ResponseBody
    public Optional<Role> getByMain(@PathVariable Long id,@PathVariable String main) throws RecordNotFoundException {
        if (roleService.getRoleByMain(id, main).isEmpty()){
            throw new RecordNotFoundException(id);
        }
        return roleService.getRoleByMain(id,main);
    }

    @GetMapping("/{main}/{category}")
    @ResponseBody
    public List<Role> getRole(@PathVariable String main,@PathVariable String category){
        return roleService.getRole(main,category);
    }


    @PatchMapping("/update/{id}/{main}")
    public void updateMain(@PathVariable Long id, @PathVariable String main){
        roleService.updateMain(id,main);
    }
    @PatchMapping("/update/{id}/{category}")
    public void updateCategory(@PathVariable Long id, @PathVariable String category){
        roleService.updateCategory(id,category);
    }

    @PatchMapping("/update/{id}/{name}")
    public void updateName(@PathVariable Long id,@PathVariable String name){
        roleService.updateName(id,name);
    }

    @PatchMapping("/update/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody RoleDto roleDto){
        var main=roleDto.main();
        var category=roleDto.category();
        var name=roleDto.roleName();
        roleService.updateRole(id,main,category,name);
    }

}
