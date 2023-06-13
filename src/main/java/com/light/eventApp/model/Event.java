package com.light.eventApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event extends AbstractBaseEntity {

    @Column(name = "header")
    @NotBlank
    @Size(min=1, max = 50)
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDate created;

    @ManyToMany(mappedBy = "eventsSet")
    private Set<User> users;


}
