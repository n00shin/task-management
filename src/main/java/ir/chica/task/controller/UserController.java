package ir.chica.task.controller;


import ir.chica.task.dto.IdsDto;
import ir.chica.task.dto.UserDto;
import ir.chica.task.exception.RecordNotFoundException;
import ir.chica.task.model.User;
import ir.chica.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final UserService service;



    @PostMapping
    public void saveUser(@Valid @RequestBody UserDto newUser) {
        service.saveUser( newUser );
    }

    @GetMapping("/{username}/{password}")
    @Validated
    public User getUserInfo(@PathVariable String username, @PathVariable String password) throws RecordNotFoundException {
        return service.getUserInfo( username, password );
    }


    @DeleteMapping("/delete/{id}")
    @Validated
    public void deleteById( @PathVariable Long id) throws RecordNotFoundException {
        service.deleteById( id );
    }


    @DeleteMapping("/delete")
    void deleteByIds(@Valid @RequestBody IdsDto idsDto) {
        service.deleteByIds( idsDto );
    }


    @PutMapping("/update/{id}")
    @Validated
    public void updatePassword( @PathVariable Long id, @Valid @RequestBody UserDto userDto) throws RecordNotFoundException {
        service.updatePassword( id, userDto );

    }


    @PutMapping("/update/{id}")
    @Validated
    public void updateEmail( @PathVariable Long id, @Valid @RequestBody UserDto userDto) throws RecordNotFoundException {
        service.updateEmail( id, userDto );

    }



//    @PostMapping("/add/user")
//    public ResponseEntity<String> validateObject(@RequestBody @Valid UserDto userDto) {
//        return new ResponseEntity<>( "Valid User!", HttpStatus.OK );
//    }
//
//    @GetMapping("/insert")
//    @ResponseBody
//    public User insertUser(@Valid User user, BindingResult result) {
//        if (result.hasErrors()) {
//            for (ObjectError error : result.getAllErrors()) {
//                System.err.println( error.getDefaultMessage() );
//            }
//        }
//        return user;
//    }


}
