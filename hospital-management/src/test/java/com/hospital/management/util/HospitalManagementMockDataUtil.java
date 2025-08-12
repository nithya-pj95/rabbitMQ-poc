package com.hospital.management.util;

import com.hospital.management.model.request.HospitalNodeRequest;
import com.hospital.management.persistence.entity.HospitalNode;

public class HospitalManagementMockDataUtil {

    public static HospitalNode getRootHospitalNode() {

        HospitalNode parent = new HospitalNode();
        parent.setId(1L);
        parent.setName("City Hospital");
        parent.setType("Hospital");
        return parent;
    }

    public static HospitalNodeRequest getHospitalNodeRequest() {
        HospitalNodeRequest request = new HospitalNodeRequest();
        request.setName("City Hospital");
        request.setType("Hospital");
        return new HospitalNodeRequest();
    }
}
