package com.light.eventApp.service;

import com.light.eventApp.model.*;
import com.light.eventApp.repository.ApplyStatusRepository;
import com.light.eventApp.repository.ContractRepository;
import com.light.eventApp.repository.EventRepository;
import com.light.eventApp.repository.UserRepository;
import com.light.eventApp.util.exception.IncorrectCreateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.light.eventApp.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ContractRepository contractRepository;
    private final ApplyStatusRepository applyStatusRepository;

    public Event create(Event event, Long userId) {
        LocalDate currentDate = LocalDate.now();
        Optional<Contract> contract = contractRepository.getContractByUserId(userId, currentDate);
        if (contract.isPresent()) {
            return saveEvent(event, userId);
        } else throw new IncorrectCreateException("No valid contract");
    }

    public Event saveEvent(Event event, Long userId){
        if (!event.isNew() && get(event.getId(), userId) == null) {
            return null;
        }
        event.setCreator(checkNotFoundWithId(userRepository.getUserById(userId),userId));
        return eventRepository.save(event);
    }
    //principal
    public Event get(Long id, Long userId) {
        return checkNotFoundWithId(eventRepository.getEventById(id, userId), id);
    }


    public void applyForEvent(ApplyStatus applyStatus) {
        checkNotFoundWithId(userRepository.getUserById(applyStatus.getId().getUserId()), applyStatus.getId().getUserId());
        checkNotFoundWithId(eventRepository.getById(applyStatus.getId().getEventId()), applyStatus.getId().getEventId());
        if (applyStatusRepository.getById(applyStatus.getId().getUserId(), applyStatus.getId().getEventId()).isPresent()) {
            throw new IncorrectCreateException("You already have applied for this event");
        } else {
            applyStatus.setCurrentStatus(CurrentStatus.APPLY);
            applyStatusRepository.save(applyStatus);
        }
    }


    //for principal
   /* public void processEventApplying (Long id, Long userId, String status) {

        ApplyStatus applyStatus = checkNotFoundWithId(applyStatusRepository.getById(userId, id), id);
        if (!(applyStatus.getCurrentStatus().equals(CurrentStatus.APPLY))) {
            throw new IncorrectUpdateException();
        } else {
            applyStatusRepository.save(userId,id, status);
        }
    }

    */

//for participant
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    // for participant
    public List<Event> getAllByCreator(Long userId) {
        return checkNotFoundWithId(eventRepository.getAllByCreator(userId), userId);
    }

    //for participant
    public List<Event> getAllParticipants(Long id) {
        return checkNotFoundWithId(eventRepository.getAllParticipants(id), id);
    }


    //for principal
    public void delete(Long id, Long userId) {
        checkNotFoundWithId(eventRepository.delete(id, userId), id);
    }

    //for principal
    public void update(Event event, Long userId) {
        checkNotFoundWithId(saveEvent(event, userId), event.getId());
    }


}
