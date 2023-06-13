package com.light.eventApp.model;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractBaseEntity {

    public static final int START_SEQ = 100000;

    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Id
    protected long id;


}
