package com.hospital.management.persistence.dao;

import com.hospital.management.persistence.entity.MessageLog;

public interface MessageLogDao {

    void saveMessageLog(MessageLog messageLog);
}
