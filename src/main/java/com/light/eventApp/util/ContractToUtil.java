package com.light.eventApp.util;

import com.light.eventApp.model.Contract;
import com.light.eventApp.model.User;
import com.light.eventApp.to.ContractTo;

public class ContractToUtil {

    public static Contract createNewFromTo(ContractTo contractTo, User user) {
        return new Contract( null, contractTo.getDetails(), contractTo.getContent(), contractTo.getDateOfSigning(), contractTo.getEndDate(), user);
    }
}
