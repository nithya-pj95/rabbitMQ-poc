package com.hospital.management.persistence.dao;

import com.hospital.management.persistence.entity.HospitalNode;

import java.util.Optional;

public interface HospitalNodeDao {

    Optional<HospitalNode> findHospitalNodeById(Long id);

    HospitalNode saveNode(HospitalNode hospitalNode);

    void deleteNode(HospitalNode hospitalNode);
}
