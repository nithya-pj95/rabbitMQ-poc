package com.hospital.management.persistence.repository;

import com.hospital.management.persistence.entity.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog,Long> {

}
