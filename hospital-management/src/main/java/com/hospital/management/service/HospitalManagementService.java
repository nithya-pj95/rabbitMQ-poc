package com.hospital.management.service;

import com.hospital.management.model.request.HospitalNodeRequest;
import com.hospital.management.model.response.HospitalNodeView;
import org.apache.coyote.BadRequestException;

public interface HospitalManagementService {

    HospitalNodeView createHospitalNode(HospitalNodeRequest hospitalNodeRequest) throws BadRequestException;
    void deleteHospitalNode(Long id);
}
