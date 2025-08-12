package com.hospital.management.persistence.dao;

import com.hospital.management.persistence.entity.HospitalNode;
import com.hospital.management.persistence.repository.HospitalNodeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HospitalNodeJpaDao implements HospitalNodeDao {

    private final HospitalNodeRepository hospitalNodeRepository;

    public HospitalNodeJpaDao(HospitalNodeRepository hospitalNodeRepository) {
        this.hospitalNodeRepository = hospitalNodeRepository;
    }

    @Override
    public Optional<HospitalNode> findHospitalNodeById(Long id) {
        return hospitalNodeRepository.findById(id);
    }

    @Override
    public HospitalNode saveNode(HospitalNode hospitalNode) {
        return hospitalNodeRepository.save(hospitalNode);
    }

    @Override
    public void deleteNode(HospitalNode hospitalNode) {
        hospitalNodeRepository.delete(hospitalNode);
    }
}
