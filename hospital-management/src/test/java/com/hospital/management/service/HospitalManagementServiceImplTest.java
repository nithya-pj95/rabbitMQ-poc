package com.hospital.management.service;

import com.hospital.management.exception.custom.DataNotFoundException;
import com.hospital.management.messaging.model.NodeMessage;
import com.hospital.management.messaging.producer.NodeMessageProducer;
import com.hospital.management.model.response.HospitalNodeView;
import com.hospital.management.persistence.dao.HospitalNodeDao;
import com.hospital.management.persistence.entity.HospitalNode;
import com.hospital.management.persistence.factory.HospitalManagementDaoFactory;
import com.hospital.management.util.HospitalManagementMockDataUtil;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HospitalManagementServiceImplTest {

    @Mock
    private HospitalManagementDaoFactory daoFactory;

    @Mock
    private HospitalNodeDao hospitalNodeDao;

    @Mock
    private NodeMessageProducer producer;

    @InjectMocks
    private HospitalManagementServiceImpl hospitalManagementService;

    @BeforeEach
    void setUp() {
        Mockito.when(daoFactory.getHospitalNodeDao()).thenReturn(hospitalNodeDao);
    }

    @Test
    void testCreateHospitalNode() throws BadRequestException {
        Mockito.when(hospitalNodeDao.saveNode(Mockito.any(HospitalNode.class)))
                .thenReturn(HospitalManagementMockDataUtil.getRootHospitalNode());
        HospitalNodeView result = hospitalManagementService.createHospitalNode(
                HospitalManagementMockDataUtil.getHospitalNodeRequest());

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("City Hospital", result.name());
        Mockito.verify(producer, Mockito.times(1)).sendCreateMessage(Mockito.any(NodeMessage.class));
    }

    @Test
    void testDeleteHospitalNode() {
        Mockito.when(hospitalNodeDao.findHospitalNodeById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(HospitalManagementMockDataUtil.getRootHospitalNode()));
        hospitalManagementService.deleteHospitalNode(1L);

        Mockito.verify(producer, Mockito.times(1)).sendDeleteMessage(Mockito.any(NodeMessage.class));
    }

    @Test
    void testDeleteHospitalNode_NotFound() {
        Long nodeId = 1L;
        Mockito.when(hospitalNodeDao.findHospitalNodeById(nodeId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> hospitalManagementService.deleteHospitalNode(nodeId));
    }
}
