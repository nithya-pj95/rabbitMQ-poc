package com.hospital.management.service;

import com.hospital.management.exception.custom.DataNotFoundException;
import com.hospital.management.messaging.model.NodeMessage;
import com.hospital.management.messaging.producer.NodeMessageProducer;
import com.hospital.management.model.request.HospitalNodeRequest;
import com.hospital.management.model.response.HospitalNodeView;
import com.hospital.management.persistence.entity.HospitalNode;
import com.hospital.management.persistence.factory.HospitalManagementDaoFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
public class HospitalManagementServiceImpl implements HospitalManagementService {

    @Autowired
    private HospitalManagementDaoFactory daoFactory;

    @Autowired
    private NodeMessageProducer producer;

    @Override
    public HospitalNodeView createHospitalNode(HospitalNodeRequest hospitalNodeRequest) throws BadRequestException {
        log.debug("Request to create new node: {}", hospitalNodeRequest);
        HospitalNode newHospitalNode = populateHospitalNodeFromRequest(hospitalNodeRequest);
        HospitalNode savedHospitalNode = daoFactory.getHospitalNodeDao().saveNode(newHospitalNode);
        producer.sendCreateMessage(populateMessage(savedHospitalNode, "CREATE"));
        return transformHospitalNodeToView(savedHospitalNode);
    }

    @Override
    public void deleteHospitalNode(Long id) {
        log.debug("Request to delete new node by id: {}", id);
        HospitalNode node = daoFactory.getHospitalNodeDao().findHospitalNodeById(id)
                .orElseThrow(() -> new DataNotFoundException("Node not found"));
        daoFactory.getHospitalNodeDao().deleteNode(node);
        producer.sendDeleteMessage(populateMessage(node, "DELETE"));
    }

    private HospitalNode populateHospitalNodeFromRequest(HospitalNodeRequest hospitalNodeRequest)
            throws BadRequestException {
        HospitalNode hospitalNode = new HospitalNode();
        hospitalNode.setName(hospitalNodeRequest.getName());
        hospitalNode.setType(hospitalNodeRequest.getType());
        Long parenId = hospitalNodeRequest.getParentId();
        if (parenId != null) {
            log.debug("Fetching parent by id: {}", parenId);
            HospitalNode parent = daoFactory.getHospitalNodeDao().findHospitalNodeById(parenId)
                    .orElseThrow(() -> new BadRequestException("Parent not found"));
            hospitalNode.setParent(parent);
        }
        return hospitalNode;
    }

    private HospitalNodeView transformHospitalNodeToView(HospitalNode hospitalNode) {
        Long parentId = hospitalNode.getParent() == null ? null : hospitalNode.getParent().getId();
        return new HospitalNodeView(hospitalNode.getId(), hospitalNode.getName(), hospitalNode.getType(), parentId);
    }

    private NodeMessage populateMessage(HospitalNode hospitalNode, String operation) {
        log.debug("populate nodeMessage for operation: {}", operation);
        NodeMessage nodeMessage = new NodeMessage();
        nodeMessage.setId(hospitalNode.getId());
        HospitalNode parent = hospitalNode.getParent();
        nodeMessage.setParentId(parent == null ? null : parent.getId());
        nodeMessage.setOperation(operation);
        nodeMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return nodeMessage;
    }

}
