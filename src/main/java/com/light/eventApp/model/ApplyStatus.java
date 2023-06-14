package com.light.eventApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_event")
public class ApplyStatus {

    @EmbeddedId
    UserEvent id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    Event event;

    @Enumerated(value = EnumType.STRING)
    @Column(name="status", columnDefinition = "APPLY")
    CurrentStatus currentStatus;
}
