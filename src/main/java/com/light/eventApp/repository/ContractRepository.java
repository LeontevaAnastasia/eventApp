package com.light.eventApp.repository;

import com.light.eventApp.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Repository
@Transactional
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("select c from Contract c where c.user.id=:userId and c.endDate>:curDate")
    Optional<Contract> getContractById(@Param("userId") Long userId, @Param("curDate") LocalDate curDate);
}

