package dev.jaffer.demonotificationservice.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaffer.demonotificationservice.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    private final AmazonSQS sqsClient;
    private final String queueUrl;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public SqsService(AmazonSQS sqsClient, @Value("${aws.sqs.queue.url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    public boolean sendNotification(Notification notification) {
        try {

            String message = objectMapper.writeValueAsString(notification);
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(message)
                    .withDelaySeconds(5);
            sqsClient.sendMessage(send_msg_request);
            return true;
        } catch (Exception e) {
            System.err.println("Error sending message to SQS: " + e.getMessage());
            return false;
        }
    }
}