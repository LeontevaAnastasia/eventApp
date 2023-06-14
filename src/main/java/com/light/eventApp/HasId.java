package com.light.eventApp;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasId {

    Long getId();

    void setId(Long id);

    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }

    default Long id() {
        return getId();
    }


}
