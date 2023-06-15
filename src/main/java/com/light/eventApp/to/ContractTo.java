package com.light.eventApp.to;

import com.light.eventApp.model.CurrentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractTo implements Serializable {

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

}
