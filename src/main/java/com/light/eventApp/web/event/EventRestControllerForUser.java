package com.light.eventApp.web.event;

import com.light.eventApp.model.Event;
import com.light.eventApp.repository.EventRepository;
import com.light.eventApp.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/eventApp/profile/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventRestControllerForUser {

    private final EventService eventService;

    @GetMapping
    public List<Event> getAll() {
        return eventService.getAll();
    }



    }
