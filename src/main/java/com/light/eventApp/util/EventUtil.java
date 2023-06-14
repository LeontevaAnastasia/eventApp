package com.light.eventApp.util;

import com.light.eventApp.model.Event;
import com.light.eventApp.model.User;
import com.light.eventApp.to.EventTo;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Set;

@UtilityClass
public class EventUtil {

    public static Event createNewFromTo(EventTo eventTo, User user) {
        return new Event(null, eventTo.getHeader(), eventTo.getDescription(), eventTo.getPrice(), eventTo.getDateTime(),LocalDate.now(), user);
    }
}
