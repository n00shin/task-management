package ir.chica.task.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String main;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String roleName;


    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    public Role(String main, String category, String roleName) {
        this.main = main;
        this.category = category;
        this.roleName=roleName;
    }

    public boolean equals(Object object){
        if (object==null){
            return false;
        }
        if (this.getClass()!=object.getClass()){
            return false;
        }
        if (this.getId() != null){
            Role role= (Role) object;
            return (this.getId().equals(role.getId()));
        }else {
            return false;
        }
    }


}