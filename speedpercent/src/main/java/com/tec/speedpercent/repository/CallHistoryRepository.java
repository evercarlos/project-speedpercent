package com.tec.speedpercent.repository;

import com.tec.speedpercent.domain.entity.CallHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {
}
