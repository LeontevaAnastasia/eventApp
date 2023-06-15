package com.light.eventApp.web.contract;

import com.light.eventApp.model.Contract;
import com.light.eventApp.model.User;
import com.light.eventApp.service.ContractService;
import com.light.eventApp.service.UserService;
import com.light.eventApp.to.ContractTo;
import com.light.eventApp.util.ContractToUtil;
import com.light.eventApp.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.light.eventApp.util.ValidationUtil.checkNew;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/eventApp/admin/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractRestControllersForAdmin {

   private final ContractService contractService;
   private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Contract> createContract(@RequestBody ContractTo contractTo) {
        Long userId = SecurityUtil.authUserId();
        User user= userService.get(userId);
        Contract contract = ContractToUtil.createNewFromTo(contractTo,user);
        checkNew(contract);
        Contract created = contractService.applyContract(contract, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/eventApp/admin/contracts/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public Contract get(@PathVariable Long id) {
        Long userId = SecurityUtil.authUserId();
        return contractService.get(id, userId);
    }
}
