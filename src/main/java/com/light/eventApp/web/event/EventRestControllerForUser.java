package com.light.eventApp.web.event;

import com.light.eventApp.model.ApplyStatus;
import com.light.eventApp.model.Event;
import com.light.eventApp.model.UserEvent;
import com.light.eventApp.repository.EventRepository;
import com.light.eventApp.service.EventService;
import com.light.eventApp.service.UserService;
import com.light.eventApp.util.SecurityUtil;
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
    private final UserService userService;
    private final EventRepository eventRepository;

    @GetMapping
    public List<Event> getAll() {
        return eventService.getAll();
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void applyForEvent(@PathVariable Long id){
        Long userId = SecurityUtil.authUserId();
        ApplyStatus applyStatus = new ApplyStatus();
        UserEvent userEvent = new UserEvent();
        applyStatus.setUser(userService.get(userId));
        applyStatus.setEvent(eventRepository.getById(id));
        userEvent.setEventId(id);
        userEvent.setUserId(userId);
        applyStatus.setId(userEvent);
        eventService.applyForEvent(applyStatus);

    }



    }
