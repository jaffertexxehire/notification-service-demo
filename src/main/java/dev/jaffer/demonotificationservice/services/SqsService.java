package dev.jaffer.demonotificationservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaffer.demonotificationservice.entity.Notification;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    //private final AmazonSQS sqsClient;
    private final String highPriorityQueueUrl;
    private final String mediumPriorityQueueUrl;
    private final String lowPriorityQueueUrl;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private SqsTemplate sqsTemplate;




    public SqsService(
            @Value("${aws.sqs.queue.highPriorityUrl}") String highPriorityQueueUrl,
            @Value("${aws.sqs.queue.mediumPriorityUrl}") String mediumPriorityQueueUrl,
            @Value("${aws.sqs.queue.lowPriorityUrl}") String lowPriorityQueueUrl) {

        this.highPriorityQueueUrl = highPriorityQueueUrl;
        this.mediumPriorityQueueUrl = mediumPriorityQueueUrl;
        this.lowPriorityQueueUrl = lowPriorityQueueUrl;
    }

    public boolean sendNotification(Notification notification) {
        try {
            String message = objectMapper.writeValueAsString(notification);
            String queueUrl = getQueueUrlForPriority(notification.getPriority());

            sqsTemplate.send(queueUrl, message);
            return true;
        } catch (Exception e) {
            System.err.println("Error sending message to SQS: " + e.getMessage());
            return false;
        }
    }

    private String getQueueUrlForPriority(String priority) {
        switch (priority) {
            case "HIGH":
                return highPriorityQueueUrl;
            case "MEDIUM":
                return mediumPriorityQueueUrl;
            case "LOW":
                return lowPriorityQueueUrl;
            default:
                throw new IllegalArgumentException("Invalid priority: " + priority);

        }
    }
}