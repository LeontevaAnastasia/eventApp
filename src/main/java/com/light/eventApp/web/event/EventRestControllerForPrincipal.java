package com.light.eventApp.web.event;

import com.light.eventApp.model.Event;
import com.light.eventApp.model.User;
import com.light.eventApp.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/eventApp/principal/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventRestControllerForPrincipal {

    private final EventService eventService;

    @GetMapping(value = "/{id}")
    public Event get(@PathVariable Long id, @PathVariable Long userId) {
        return eventService.get(id, userId);
    }

    @GetMapping("/{id}/participants")
    public List<User> getAllParticipants(@PathVariable Long id) {
        return eventService.getAllParticipants(id);
    }

    @GetMapping("/{id}/all")
    public List<Event> getAllByCreator(@PathVariable Long id) {
        return eventService.getAllByCreator(id);
    }

    @GetMapping("/all")
    public List<Event> getAll() {
        return eventService.getAll();
    }
}
