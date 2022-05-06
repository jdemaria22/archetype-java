package ${groupId}.reactive;

import ${groupId}.reactive.transport.ObjectMapperConverter;
import ${groupId}.reactive.model.MessageTransport;
import ${groupId}.reactive.transport.ProcessorEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Value("${activemq.broker-queue-logger}")
    private String queueLogger;
    @Value("${activemq.broker-topic-processed}")
    private String topicProcessor;

    @Autowired
    private ObjectMapperConverter objectMapperConverter;
    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "${activemq.broker-queue-processor}")
    public void receiveFromQueue(String message) {
        LOGGER.info(":::PROCESSING RECEIVER QUEUE :::");
        ProcessorEvent<MessageTransport> productEvent = convert(message);
        String productEventJson = objectMapperConverter
                .toJson(productEvent);
        jmsTemplate.send(queueLogger, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(productEventJson);
                return message;
            }
        });
    }

    @JmsListener(destination = "${activemq.broker-topic-processor}", containerFactory = "topicJmsListenerContainerFactory")
    public void receiveFromTopic(String message) {
        LOGGER.info(":::PROCESSING RECEIVER TOPIC :::");
        ProcessorEvent<MessageTransport> productEvent = convert(message);
        String productEventJson = objectMapperConverter.toJson(productEvent);
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.convertAndSend(topicProcessor, productEventJson);

    }

    private ProcessorEvent<MessageTransport> convert(String message) {
        try {
            return objectMapperConverter
                    .getMapper()
                    .readValue(message, new TypeReference<ProcessorEvent>() {
                    });
        } catch (IOException ex) {
            //TODO: Handle exception
            return new ProcessorEvent();
        }
    }
}