package com.corcino.users.infrastructure.component;

import com.corcino.users.domain.entities.MessageLogger;
import com.corcino.users.domain.entities.User;
import com.corcino.users.infrastructure.repository.MessageLoggerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class RabbitMQConsumerComponent {

    private final MailMessageComponent mailMessageComponent;
    private final MessageLoggerRepository loggerRepository;
    private final ObjectMapper mapper;

    @Autowired
    public RabbitMQConsumerComponent(MailMessageComponent mailMessageComponent, MessageLoggerRepository loggerRepository, ObjectMapper mapper) {
        this.mailMessageComponent = mailMessageComponent;
        this.loggerRepository = loggerRepository;
        this.mapper = mapper;
    }

    @RabbitListener(queues = { "${queue.name}" })
    public void proccessMessage(@Payload String message) {

        MessageLogger messageLogger = new MessageLogger();
        messageLogger.setMessageLoggerId(UUID.randomUUID());
        messageLogger.setCreatedAt(Instant.now());

        try {
            User user = mapper.readValue(message, User.class);

            String to = user.getEmail();
            String subject = "Parabéns, seu cadastro foi realizado com sucesso!";
            String body = "Olá, " + user.getName() + "\nSua conta de usuário foi criada no sistema!\n\nAtt\nEquipe COTI";

            mailMessageComponent.send(to, subject, body);

            messageLogger.setStatus("SUCESSO");
            messageLogger.setUser(user);
            messageLogger.setMessage("Email de boas vindas enviado com sucesso para: " + user.getEmail());
        }
        catch(Exception e) {
            messageLogger.setStatus("ERRO");
            messageLogger.setMessage(e.getMessage());
        }
        finally {
            loggerRepository.save(messageLogger);
        }
    }

}
