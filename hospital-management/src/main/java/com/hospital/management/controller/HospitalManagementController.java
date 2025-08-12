package com.hospital.management.controller;

import com.hospital.management.model.request.HospitalNodeRequest;
import com.hospital.management.model.response.HospitalNodeView;
import com.hospital.management.service.HospitalManagementService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hospital-management")
public class HospitalManagementController {

    @Autowired
    private HospitalManagementService hospitalManagementService;

    /**
     * POST method to create a hospital node
     *
     * @param hospitalNodeRequest the node creation request
     * throws {@link BadRequestException}
     * returns {@link HospitalNodeView}
     */
    @PostMapping("/node")
    public ResponseEntity<HospitalNodeView> createHospitalNode(@RequestBody HospitalNodeRequest hospitalNodeRequest)
            throws BadRequestException {
        return ResponseEntity.ok(hospitalManagementService.createHospitalNode(hospitalNodeRequest));
    }

    /**
     * DELETE method to delete a hospital node
     *
     * @param id the id of node to be deleted
     */
    @DeleteMapping("/node/id/{id}")
    public ResponseEntity<Void> deleteHospitalNode(@PathVariable Long id) {
        hospitalManagementService.deleteHospitalNode(id);
        return ResponseEntity.noContent().build();
    }
}
