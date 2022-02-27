package ir.chica.task.service;


import ir.chica.task.dto.IdsDto;
import ir.chica.task.dto.UserDto;
import ir.chica.task.exception.RecordNotFoundException;
import ir.chica.task.model.User;
import ir.chica.task.repository.RoleRepository;
import ir.chica.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public User findUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedIsFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found by (" + email + ") email" ));
    }


    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedIsFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found by (" + username + ") username" ));
    }



    public void saveUser(UserDto userDto) {
        userRepository.save( User.fromDto( userDto ) );
    }

//    public void saveUser(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setLocked(false);
//        Role userRole = roleRepository.findByRoleName( "USER" );
//        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
//        userRepository.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsernameAndDeletedIsFalse( username )
                .orElseThrow( () -> new UsernameNotFoundException( "username not found" ) );
    }



    public User getUserInfo(String username, String password) throws RecordNotFoundException {
        Optional<User> userOptional = userRepository.findByUsernameAndDeletedIsFalse( username );
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().matches( password )) {
                return user;
            } else {
                throw new RecordNotFoundException( "user not found by (" + password + ") password" );
            }
        } else {
            throw new RecordNotFoundException( "user not found by (" + username + ") username" );
        }

    }

    public void updatePassword(Long id, UserDto userDto) throws RecordNotFoundException {
        var password = userDto.password();
        if (userRepository.existsById( id )) {
            userRepository.updatePassword( id, password );

        } else {
            throw new RecordNotFoundException( id );
        }
    }

    public void updateEmail(Long id, UserDto userDto) throws RecordNotFoundException {
        var email = userDto.email();
        if (userRepository.existsById( id )) {
            userRepository.updateEmail( id, email );

        } else {
            throw new RecordNotFoundException( id );
        }
    }

    public void deleteById(Long id) throws RecordNotFoundException {
        if (userRepository.existsById( id )) {
            userRepository.deleteById( id );
        } else {
            throw new RecordNotFoundException( id );
        }

    }


    public void deleteByIds(IdsDto idsDto) {
        var ids = idsDto.ids();
        userRepository.deleteByIds( ids );
    }


}


