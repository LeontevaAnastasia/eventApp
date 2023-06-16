package com.light.eventApp.web.contract;

import com.light.eventApp.service.ContractService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/eventApp/principal/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractRestControllerForPrincipal {

    private final ContractService contractService;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processApp(@PathVariable Long id, @RequestParam String status) {
        log.info("Get contract by id {}.", id);
        contractService.processContract(id, status);
    }
}
