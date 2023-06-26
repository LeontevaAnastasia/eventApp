package com.light.eventApp.util;

import com.light.eventApp.model.Event;
import com.light.eventApp.model.User;
import com.light.eventApp.to.EventTo;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class EventUtil {

    public static Event createNewFromTo(EventTo eventTo, User user) {
        return new Event(null, eventTo.getHeader(), eventTo.getDescription(), eventTo.getPrice(), eventTo.getDateTime(),LocalDate.now(), user);
    }

    public static Event updateFromTo(Event event, EventTo eventTo) {
        event.setHeader(eventTo.getHeader());
        event.setDescription(eventTo.getDescription());
        event.setPrice(eventTo.getPrice());
        event.setDateTime(eventTo.getDateTime());
        return event;
    }

    public static EventTo asTo(Event event) {
        return new EventTo(event.getId(),event.getHeader(), event.getDescription(), event.getPrice(), event.getDateTime());
    }
}
