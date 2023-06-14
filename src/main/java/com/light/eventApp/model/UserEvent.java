package com.light.eventApp.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "event_id")
    Long eventId;


}
