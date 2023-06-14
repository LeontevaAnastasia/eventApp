package com.light.eventApp.service;

import com.light.eventApp.model.*;
import com.light.eventApp.repository.ApplyStatusRepository;
import com.light.eventApp.repository.ContractRepository;
import com.light.eventApp.repository.EventRepository;
import com.light.eventApp.repository.UserRepository;
import com.light.eventApp.util.exception.IncorrectCreateException;
import com.light.eventApp.util.exception.IncorrectUpdateException;
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

    //for admin
    public Event create(Event event, Long userId) {
        //проверить заключен ли договор
        LocalDate currentDate = LocalDate.now();
        Optional<Contract> contract = contractRepository.getContractByUserId(userId, currentDate);
        if (contract.isPresent()) {
            return saveEvent(event, userId);
        } else throw new IncorrectCreateException("No valid contract");
    }

    public Event saveEvent(Event event, Long userId){
        if (!event.isNew() && get(event.getId()) == null) {
            return null;
        }
        event.setCreator(checkNotFoundWithId(userRepository.getUserById(userId),userId));
        return eventRepository.save(event);
    }

     //
    public Event get(Long id) {
        return checkNotFoundWithId(eventRepository.getById(id), id);
    }



//for user
    public void applyForEvent(Long id, Long userId) {
        checkNotFoundWithId(eventRepository.getById(id), id);
        checkNotFoundWithId(userRepository.getUserById(userId), userId);
        applyStatusRepository.save(id, userId, CurrentStatus.APPLY);
    }


    //for principal
    public void processEventApplying (Long id, Long userId, String status) {

        ApplyStatus applyStatus = checkNotFoundWithId(applyStatusRepository.getById(userId, id), id);
        if (!(applyStatus.getCurrentStatus().equals(CurrentStatus.APPLY))) {
            throw new IncorrectUpdateException();
        } else {
            applyStatusRepository.save(userId,id, CurrentStatus.valueOf(status));
        }
    }

//for participant
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    //for admin and for participant
    public List<Event> getAllByCreator(Long userId) {
        return checkNotFoundWithId(eventRepository.getAllByCreator(userId), userId);
    }

    //for admin and participant
    public List<Event> getAllParticipants(Long id) {
        return checkNotFoundWithId(eventRepository.getAllParticipants(id), id);
    }


    //for admin and for principal
    public void delete(Long id, Long userId) {
        checkNotFoundWithId(eventRepository.delete(id, userId), id);
    }




}
