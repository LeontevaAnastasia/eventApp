package com.light.eventApp.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTo extends AbstractBaseTo{

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

}
