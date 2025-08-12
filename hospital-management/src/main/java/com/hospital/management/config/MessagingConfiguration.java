package com.hospital.management.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configurations
 */
@Configuration
public class MessagingConfiguration {

    public static final String CREATE_QUEUE_NAME = "hospital.node.create.queue";

    public static final String DELETE_QUEUE_NAME = "hospital.node.delete.queue";

    public static final String DLQ_NAME = "hospital.node.dlqueue";

    public static final String EXCHANGE_NAME = "hospital.node.exchange";

    public static final String DLX = "hospital.node.dlx";

    public static final String CREATE_ROUTING_KEY = "hospital.node.create";

    public static final String DELETE_ROUTING_KEY = "hospital.node.delete";

    public static final String DL_ROUTING_KEY = "hospital.node.dlrk";

    @Bean
    public Queue createQueue() {
        return QueueBuilder.durable(CREATE_QUEUE_NAME).ttl(10000).deadLetterExchange(DLX)
                .deadLetterRoutingKey(DL_ROUTING_KEY).build();
    }

    @Bean
    public Queue deleteQueue() {
        return QueueBuilder.durable(DELETE_QUEUE_NAME).ttl(10000).deadLetterExchange(DLX)
                .deadLetterRoutingKey(DL_ROUTING_KEY).build();
    }

    @Bean
    public Queue dlQueue() {
        return QueueBuilder.durable(DLQ_NAME).build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange dlExchange() {
        return new DirectExchange(DLX);
    }

    @Bean
    public Binding createBinding(Queue createQueue, DirectExchange exchange) {
        return BindingBuilder.bind(createQueue).to(exchange).with(CREATE_ROUTING_KEY);
    }

    @Bean
    public Binding deleteBinding(Queue deleteQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteQueue).to(exchange).with(DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding dlqBinding(Queue dlQueue, DirectExchange dlExchange) {
        return BindingBuilder.bind(dlQueue).to(dlExchange).with(DL_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
