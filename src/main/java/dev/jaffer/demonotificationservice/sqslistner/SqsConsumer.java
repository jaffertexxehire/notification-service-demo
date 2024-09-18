package dev.jaffer.demonotificationservice.sqslistner;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaffer.demonotificationservice.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class SqsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SqsConsumer.class);

    private SqsClient sqsClient;

    public SqsConsumer(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${aws.sqs.queue.url}")
    private String queueUrl;

    @Scheduled(fixedDelay = 1000) // Poll every second
    public void receiveMessages() {
        try {
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(10)
                    .waitTimeSeconds(20)
                    .build();

            List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

            for (Message message : messages) {
                processMessage(message);
                System.out.println("processed by" + Thread.currentThread().getName());
                deleteMessage(message);
            }
        } catch (Exception e) {
            logger.error("Error receiving messages from SQS", e);
        }
    }

    private void processMessage(Message message) {
        try {
            String messageBody = message.body();
            logger.info("Received message: {}", messageBody);

            // Assuming the message body is a JSON representation of a Notification
            Notification notification = objectMapper.readValue(messageBody, Notification.class);
            logger.info("Processed notification: {}", notification);

            // Add your notification processing logic here
        } catch (Exception e) {
            logger.error("Error processing message: {}", message.messageId(), e);
        }
    }

    private void deleteMessage(Message message) {
        try {
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();

            sqsClient.deleteMessage(deleteRequest);
            logger.info("Deleted message: {}", message.messageId());
        } catch (Exception e) {
            logger.error("Error deleting message: {}", message.messageId(), e);
        }
    }
}
