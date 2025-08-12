package com.hospital.management.messaging.consumer;

import com.hospital.management.config.MessagingConfiguration;
import com.hospital.management.messaging.model.NodeMessage;
import com.hospital.management.persistence.entity.MessageLog;
import com.hospital.management.persistence.factory.HospitalManagementDaoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Message Consumer
 */
@Service
@Slf4j
public class NodeMessageConsumer {

    @Autowired
    HospitalManagementDaoFactory daoFactory;

    /**
     * Listens to CREATE node operation
     * throws {@link AmqpRejectAndDontRequeueException}
     */
    @RabbitListener(queues = MessagingConfiguration.CREATE_QUEUE_NAME, concurrency = "3-5") // 3-5 concurrent consumers.
    public void receiveCreateMessage(NodeMessage nodeMessage) {
        try {
            log.info("Received create node nodeMessage: " + nodeMessage);
            saveMessageLog(nodeMessage);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Create message failed...",
                    e); //reject the message and send to dlq
        }
    }

    /**
     * Listens to DELETE node operation
     * throws {@link AmqpRejectAndDontRequeueException}
     */
    @RabbitListener(queues = MessagingConfiguration.DELETE_QUEUE_NAME)
    public void receiveDeleteMessage(NodeMessage nodeMessage) {
        try {
            log.info("Received delete node nodeMessage: " + nodeMessage);
            saveMessageLog(nodeMessage);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Delete message failed...", e);
        }
    }

    /**
     * Consumed messages are logged in db for auditing
     */
    private void saveMessageLog(NodeMessage nodeMessage) {
        MessageLog messageLog = new MessageLog();
        messageLog.setOperation(nodeMessage.getOperation());
        messageLog.setMessageTimeStamp(nodeMessage.getTimestamp());
        String messageLine = nodeMessage.getOperation() + " node " + nodeMessage.getId() + " parent: " + nodeMessage.getParentId();
        messageLog.setMessage(messageLine);
        daoFactory.getMessageLogDao().saveMessageLog(messageLog);
    }
}
