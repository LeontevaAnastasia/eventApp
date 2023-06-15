package com.light.eventApp.web.contract;

import com.light.eventApp.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/eventApp/principal/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractRestControllerForPrincipal {

    private final ContractService contractService;
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processApp(@PathVariable Long id, @RequestParam String status) {
        contractService.processContract(id, status);
    }
}
