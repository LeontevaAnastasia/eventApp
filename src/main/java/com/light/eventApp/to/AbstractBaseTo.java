package com.light.eventApp.to;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.light.eventApp.HasId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractBaseTo implements HasId {

    @JsonIgnore
    protected Long id;
}
