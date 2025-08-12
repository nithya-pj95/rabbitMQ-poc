package com.hospital.management.persistence.factory;

import com.hospital.management.persistence.dao.HospitalNodeDao;
import com.hospital.management.persistence.dao.MessageLogDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class HospitalManagementDaoFactory {

    @Autowired
    HospitalNodeDao hospitalNodeDao;

    @Autowired
    MessageLogDao messageLogDao;
}
