package com.light.eventApp.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventTo extends AbstractBaseTo implements Serializable {

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

    public EventTo(Long id, String header, String description, double price, LocalDateTime dateTime) {
        super(id);
        this.header = header;
        this.description = description;
        this.price = price;
        this.dateTime = dateTime;
    }

}
