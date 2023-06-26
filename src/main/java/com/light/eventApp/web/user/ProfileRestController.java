package com.light.eventApp.web.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.light.eventApp.AuthorizedUser;
import com.light.eventApp.model.User;
import com.light.eventApp.service.UserService;
import com.light.eventApp.to.UserTo;
import com.light.eventApp.util.UserUtil;
import com.light.eventApp.util.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.light.eventApp.util.UserUtil.createNewFromTo;
import static com.light.eventApp.util.ValidationUtil.assureIdConsistent;
import static com.light.eventApp.util.ValidationUtil.checkNew;

@RestController
@AllArgsConstructor
@RequestMapping(value="/eventApp", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private ObjectMapper objectMapper;
    @PostMapping(value ="/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("Create profile");
        checkNew(userTo);
        User created = userService.create(createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/eventApp/profile")
                .build()
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value ="/profile")
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Get user by id {}.", authUser.getId());
        return userService.get(authUser.getId());
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Delete profile id {} by user.", authUser.getId());
        userService.delete(authUser.getId());
    }


    @PatchMapping(consumes = "application/json-patch+json")
    public ResponseEntity<UserTo> update(@RequestBody JsonMergePatch patch,
                                         @Parameter(hidden = true)@AuthenticationPrincipal AuthorizedUser authUser) {
        try {
            log.info("Update user with id {}.", authUser.getId());
            User user = userService.get(authUser.getId());
            UserTo userTo = UserUtil.asTo(user);
            UserTo userPatched = applyPatchToUser(patch, userTo);
            userService.update(UserUtil.updateFromTo(user, userPatched));
            return ResponseEntity.ok(userTo);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private UserTo applyPatchToUser(
            JsonMergePatch patch, UserTo targetUserTo) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetUserTo, JsonNode.class));
        return objectMapper.treeToValue(patched, UserTo.class);
    }

    @GetMapping(value ="/admin/profile")
    public User getAdmin(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Get admin by id {}.", authUser.getId());
        return userService.get(authUser.getId());
    }
}
