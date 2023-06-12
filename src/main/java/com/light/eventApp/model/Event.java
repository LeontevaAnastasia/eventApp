package com.light.eventApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Event {

    private String name;

    private double price;

    private LocalDateTime dateTime;

    private Set<User> users;


}
