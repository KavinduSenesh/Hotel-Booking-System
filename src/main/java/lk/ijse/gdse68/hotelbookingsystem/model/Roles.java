package lk.ijse.gdse68.hotelbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Roles(String name) {
        this.name = name;
    }

    public void assignRolesToUser(User user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUserFromRole(User user){
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    public void removeAllUsersFromRoles(){
        if(this.getUsers() != null){
            List<User> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this::removeUserFromRole);
        }
    }

    public String getName(){
        return name != null ? name : "";
    }
}
