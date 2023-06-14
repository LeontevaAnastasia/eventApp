package com.light.eventApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event extends AbstractBaseEntity {

    @Column(name = "header")
    @NotBlank
    @Size(min = 1, max = 50)
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", nullable = false)
    // @JsonBackReference
    private User creator;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    Set<ApplyStatus> statuses;


    public Event(Long id, String header, String description, double price, LocalDateTime dateTime, LocalDate created, User creator) {
        super(id);
        this.header = header;
        this.description = description;
        this.price = price;
        this.dateTime = dateTime;
        this.created=created;
        this.creator = creator;
    }
}
