package ir.chica.task.controller;


import ir.chica.task.aspect.MethodDurationLog;
import ir.chica.task.security.JwtTokenUtil;
import ir.chica.task.dto.LoginDto;
import ir.chica.task.dto.SignUpDto;
import ir.chica.task.model.Role;
import ir.chica.task.model.User;
import ir.chica.task.repository.RoleRepository;
import ir.chica.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;



    @PostMapping("login")
    @MethodDurationLog
    public ResponseEntity<Object>
    login(@RequestBody @Valid LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate( new UsernamePasswordAuthenticationToken(
                            loginDto.username(), loginDto.password() ) );

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.accepted().body( jwtTokenUtil.generateAccessToken( user ));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).build();
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }


        // create user object
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = (Role) roleRepository.findByRoleName("ROLE_ADMIN").getUserRoles();
        user.setRoles( Collections.singleton(roles));


        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}


