package com.hospital.management.messaging.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Custom message
 */
@Data
public class NodeMessage {

    private Long id;

    private Long parentId;

    private String operation;

    private Timestamp timestamp;
}
