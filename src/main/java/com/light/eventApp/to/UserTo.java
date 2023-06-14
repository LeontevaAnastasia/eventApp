package com.light.eventApp.to;

import com.light.eventApp.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTo extends AbstractBaseTo implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Size(min =1, max = 128)
    private String name;


    @Size(min=1, max = 128)
    private String email;

    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "age")
    @Size(min = 14, max = 100)
    private int age;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;



    public UserTo(Long id, String name, String email, String password, int age) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.age=age;
    }
}
