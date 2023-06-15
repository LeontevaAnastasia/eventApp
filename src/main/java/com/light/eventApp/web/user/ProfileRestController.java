package com.light.eventApp.web.user;

import com.light.eventApp.AuthorizedUser;
import com.light.eventApp.model.User;
import com.light.eventApp.service.UserService;
import com.light.eventApp.to.UserTo;
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

    @PutMapping(value ="/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Update user to {} by user id {}.", userTo, authUser.getId());
        assureIdConsistent(userTo, authUser.getId());
        userService.update(userTo);
    }

    @GetMapping(value ="/admin/profile")
    public User getAdmin(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Get admin by id {}.", authUser.getId());
        return userService.get(authUser.getId());
    }
}
