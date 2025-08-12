package com.hospital.management.persistence.dao;

import com.hospital.management.persistence.entity.MessageLog;
import com.hospital.management.persistence.repository.MessageLogRepository;
import org.springframework.stereotype.Component;

@Component
public class MessageLogJpaDao implements MessageLogDao {

    private final MessageLogRepository messageLogRepository;

    public MessageLogJpaDao(MessageLogRepository messageLogRepository) {
        this.messageLogRepository = messageLogRepository;
    }

    @Override
    public void saveMessageLog(MessageLog messageLog) {
        messageLogRepository.save(messageLog);
    }
}
