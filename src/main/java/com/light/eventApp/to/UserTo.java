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



    public UserTo(Long id, String name, String email, String password, int age) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.age=age;
    }
}
