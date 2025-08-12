package com.hospital.management.messaging.producer;

import com.hospital.management.config.MessagingConfiguration;
import com.hospital.management.messaging.model.NodeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Message Producer
 */
@Service
@Slf4j
public class NodeMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public NodeMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCreateMessage(NodeMessage nodeMessage) {
        log.info("Sending create nodeMessage...{}", nodeMessage);
        rabbitTemplate.convertAndSend(MessagingConfiguration.EXCHANGE_NAME, MessagingConfiguration.CREATE_ROUTING_KEY,
                nodeMessage); // converts the nodeMessage Object using Jackson2JsonMessageConverter
    }

    public void sendDeleteMessage(NodeMessage nodeMessage) {
        log.info("Sending delete nodeMessage...{}", nodeMessage);
        rabbitTemplate.convertAndSend(MessagingConfiguration.EXCHANGE_NAME, MessagingConfiguration.DELETE_ROUTING_KEY,
                nodeMessage);
    }
}
