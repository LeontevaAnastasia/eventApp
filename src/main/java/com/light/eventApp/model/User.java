package com.light.eventApp.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {

    @Column(name = "name")
    @Size(min =1, max = 128)
    private String name;

    @Column(name = "email")
    @Email
    @NotEmpty
    @Size(min=1, max = 128)
    private String email;

    @Column(name = "password")
    @Size(min = 4, max = 100)
    @NotEmpty
    private String password;

    @Column(name = "age")
    @Size(min = 14, max = 100)
    private int age;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDate registered;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private Set<Event> eventsSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Contract> contractsSet;

    private CurrentStatus status;

}
