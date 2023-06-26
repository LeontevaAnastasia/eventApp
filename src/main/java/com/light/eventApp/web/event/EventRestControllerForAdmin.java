package com.light.eventApp.web.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.light.eventApp.model.Event;
import com.light.eventApp.model.User;
import com.light.eventApp.service.EventService;
import com.light.eventApp.service.UserService;
import com.light.eventApp.to.EventTo;
import com.light.eventApp.util.EventUtil;
import com.light.eventApp.util.SecurityUtil;
import com.light.eventApp.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Event> create(@RequestBody EventTo eventTo) {
        log.info("Create event.");
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

    @PatchMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processApp(@RequestParam Long id,@RequestParam Long userId, @RequestParam String status) {
        log.info("Change user status");
        eventService.processEventApplying(id, userId,status);
    }

    @GetMapping
    public List<Event> getAll() {
        log.info("Get all events");
        Long userId = SecurityUtil.authUserId();
        return eventService.getAllByCreator(userId);
    }

    @GetMapping("/{id}/participants")
    public List<User> getAllParticipants(@PathVariable Long id) {
        log.info("Get all participants  by event id {}.", id);
        return eventService.getAllParticipants(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Delete event with id {}.", id);
        Long userId = SecurityUtil.authUserId();
        eventService.delete(id, userId);
    }


    @PatchMapping(path = "/{id}/update", consumes = "application/json-patch+json")
    public ResponseEntity<EventTo> update(@PathVariable Long id, @RequestBody JsonMergePatch patch) {

        try {
            Long userId = SecurityUtil.authUserId();
            log.info("Update event with id {} and userId {}.", id, userId);
            Event event = eventService.get(id,userId);
            EventTo eventTo = EventUtil.asTo(event);
            EventTo taskPatched = applyPatchToEvent(patch, eventTo);
            eventService.update(EventUtil.updateFromTo(event,taskPatched), userId);
            return ResponseEntity.ok(eventTo);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private EventTo applyPatchToEvent(
            JsonMergePatch patch, EventTo targetEventTo) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetEventTo, JsonNode.class));
        return objectMapper.treeToValue(patched, EventTo.class);
    }

}
