package com.light.eventApp.service;

import com.light.eventApp.model.Contract;
import com.light.eventApp.model.CurrentStatus;
import com.light.eventApp.repository.ContractRepository;
import com.light.eventApp.repository.UserRepository;
import com.light.eventApp.util.exception.IncorrectUpdateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.light.eventApp.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    public Contract applyContract(Contract contract, Long userId) {
        return saveContractApplying(contract, userId);
    }

    public Contract saveContractApplying(Contract contract, Long userId){
        if (!contract.isNew() && get(contract.getId(), userId) == null) {
            return null;
        }
        contract.setUser(userRepository.getById(userId));
        contract.setStatus(CurrentStatus.APPLY);
        return contractRepository.save(contract);
    }
    public Contract get(Long id, Long userId) {
        LocalDate currentDate = LocalDate.now();
        return checkNotFoundWithId(contractRepository.getCurrentContractById(id, userId, currentDate), id);
    }

    public void processContract (Long id, String status) {

        Contract contract = checkNotFoundWithId(contractRepository.findById(id), id);
            if(!(contract.getStatus().equals(CurrentStatus.APPLY))) {
                throw new IncorrectUpdateException();
            } else {
                contract.setStatus(CurrentStatus.valueOf(status));
                contractRepository.save(contract);
            }
    }

}
