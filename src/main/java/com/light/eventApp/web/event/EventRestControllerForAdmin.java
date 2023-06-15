package com.light.eventApp.web.event;

import com.light.eventApp.model.Event;
import com.light.eventApp.model.User;
import com.light.eventApp.service.EventService;
import com.light.eventApp.service.UserService;
import com.light.eventApp.to.EventTo;
import com.light.eventApp.util.EventUtil;
import com.light.eventApp.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.light.eventApp.util.ValidationUtil.assureIdConsistent;
import static com.light.eventApp.util.ValidationUtil.checkNew;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/eventApp/admin/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventRestControllerForAdmin {

    private final EventService eventService;
    private final UserService userService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Event> create(@RequestBody EventTo eventTo) {
        Long userId = SecurityUtil.authUserId();
        User user= userService.get(userId);
        Event event = EventUtil.createNewFromTo(eventTo,user);
        checkNew(event);
        Event created = eventService.create(event, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/eventApp/admin/events/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public List<Event> getAll() {
        Long userId = SecurityUtil.authUserId();
        return eventService.getAllByCreator(userId);
    }

    @GetMapping("/{id}/participants")
    public List<Event> getAllParticipants(@PathVariable Long id) {
        return eventService.getAllParticipants(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Long userId = SecurityUtil.authUserId();
        eventService.delete(id, userId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody EventTo eventTo, @PathVariable Long id) {
        Long userId = SecurityUtil.authUserId();
        assureIdConsistent(eventTo, id);
        eventService.update(EventUtil.updateFromTo((eventService.get(id, userId)) ,eventTo) , userId);
    }

}
