package com.light.eventApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {

    private String name;

    private String email;

    private String password;

    private int age;

    private boolean enabled = true;

    private LocalDate registered;

    private Set<Role> roles;

    private Set<Event> events;

    private Set<Contract> contracts;


}
