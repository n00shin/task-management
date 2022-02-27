package ir.chica.task.model;


import ir.chica.task.dto.UserDto;
import ir.chica.task.validator.PasswordConfirmation;
import ir.chica.task.validator.PhoneNumber;
import ir.chica.task.validator.Sex;
import ir.chica.task.validator.ValidEmail;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app-user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Size(min = 8, max = 16, message = "Password should between 8 und 16 characters!")
    @Column(nullable = false)
    @NotBlank
    @PasswordConfirmation(password = "password", confirmPassword = "confirmPassword",
            message = "Password and confirmation password must be the same!")
    private String password;
    @Column(nullable = false, unique = true)
    @ValidEmail
    @NotBlank
    private String email;
    @PhoneNumber(message = "This phone number is not valid")
    @NotBlank
    private String phoneNumber;
    @Sex(message = "Enter valid gender")
    @NotBlank
    private String sex;
    private boolean locked;
    private boolean deleted;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> taskList;

    @OneToMany(mappedBy = "user")
    @JoinColumn(name = "role_id")
    private List<UserRole> userRoles;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new HashSet<GrantedAuthority>();
        userRoles.forEach( ur -> authorities.add( new SimpleGrantedAuthority( ur.getRole().getRoleName() ) ) );
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !deleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !deleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !deleted;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }

    public static User fromDto(UserDto userDto) {
        User user = new User();
        user.setUsername( userDto.username() );
        user.setPassword( userDto.password() );
        user.setEmail( userDto.email() );
        user.setPhoneNumber( userDto.phoneNumber() );
        user.setSex( userDto.sex() );
        return user;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        if (this.getId() != null) {
            User user = (User) object;
            return this.getId().equals( user.getId() );
        } else {
            return false;
        }
    }


    public void setRoles(Set<Role> singleton) {
    }
}
