package com.hospital.management.model.request;

import lombok.Data;

/**
 * Request to add a hospital node
 */
@Data
public class HospitalNodeRequest {

    private String name;

    private String type;

    private Long parentId;
}
