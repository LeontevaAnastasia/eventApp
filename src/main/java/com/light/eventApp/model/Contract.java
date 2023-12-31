package com.light.eventApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "status", columnDefinition = "APPLY")
    private CurrentStatus status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Contract(Long id, String details, String content, LocalDate dateOfSigning, LocalDate endDate, User user) {
        super(id);
        this.details = details;
        this.content = content;
        this.dateOfSigning = dateOfSigning;
        this.endDate = endDate;
        this.user = user;
    }

}
