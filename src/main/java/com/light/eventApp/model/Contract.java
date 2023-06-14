package com.light.eventApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract extends AbstractBaseEntity {

    @Column(name = "details")
    @NotBlank
    @Size(min=1, max = 50)
    private String details;

    @Column(name = "content")
    @NotBlank
    private String content;


    @Column(name = "date_of_signing")
    @NotNull
    private LocalDate dateOfSigning;

    @Column(name = "end_date")
    @NotNull
    private LocalDate endDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", columnDefinition = "")
    private CurrentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;




}
