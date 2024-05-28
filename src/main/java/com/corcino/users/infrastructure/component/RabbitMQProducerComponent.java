package com.corcino.users.infrastructure.component;

import com.corcino.users.domain.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducerComponent {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;
    private final Queue queue;

    @Autowired
    public RabbitMQProducerComponent(RabbitTemplate rabbitTemplate, ObjectMapper mapper, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
        this.queue = queue;
    }

    public void sendMessage(User user) throws Exception {
        String json = mapper.writeValueAsString(user);
        rabbitTemplate.convertAndSend(queue.getName(), json);
    }

}
