package com.hospital.management.persistence.repository;

import com.hospital.management.persistence.entity.HospitalNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalNodeRepository extends JpaRepository< HospitalNode,Long> {

}
