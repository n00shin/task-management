package ir.chica.task.service;


import ir.chica.task.model.Role;
import ir.chica.task.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public void saveRole(String main, String category, String name) {
        Role role = new Role( main, category, name );
        roleRepository.save( role );

    }

    public List<Role> getAll() {
        return (List<Role>) roleRepository.findAll();
    }


    public Optional<Role> getRole(Long id) {
        Optional<Role> roleOptional = roleRepository.findById( id );
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            return Optional.of( role );
        }
        return Optional.empty();
    }


    public Optional<Role> getRoleByMain(Long id, String main) {
        Optional<Role> roleOptional = roleRepository.findByIdAndMain( id, main );
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            return Optional.of( role );
        }
        return Optional.empty();
    }

    public List<Role> getRole(String main, String category) {
        ArrayList<Role> roles = new ArrayList<>();
        roleRepository.findByMainAndCategory( main, category ).forEach( roles::add );
        return roles;
    }

    public void updateMain(Long id, String main) {
        roleRepository.updateMain( id, main );
    }


    public void updateCategory(Long id, String category) {
        roleRepository.updateCategory( id, category );
    }


    public void updateName(Long id, String name) {
        roleRepository.updateName( id, name );
    }


    public void updateRole(Long id, String main, String category, String name) {
        roleRepository.updateRole( id, main, category, name );
    }

}
