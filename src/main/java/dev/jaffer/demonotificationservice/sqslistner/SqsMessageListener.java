package dev.jaffer.demonotificationservice.sqslistner;



import dev.jaffer.demonotificationservice.notificationdispatch.SqsMessageProcessor;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;
/*
    *USe this as the listner not SQS consumer class
    * This class listens to the SQS queues and processes the messages
 */
@Service
public class SqsMessageListener {

    private SqsMessageProcessor sqsMessageProcessor;

    public SqsMessageListener(SqsMessageProcessor sqsMessageProcessor) {
        this.sqsMessageProcessor = sqsMessageProcessor;
    }

    @SqsListener(value = "${aws.sqs.queue.highPriorityUrl}")
    public void receiveHighPriorityMessage(String message) {
        System.out.println("Received high priority message: " + Thread.currentThread().getName());
        sqsMessageProcessor.processMessage(message);

    }


    @SqsListener(value = "${aws.sqs.queue.mediumPriorityUrl}")
    public void receiveMediumPriorityMessage(String message) {
        System.out.println("Received medium priority message: " + Thread.currentThread().getName());
        sqsMessageProcessor.processMessage(message);
    }


    @SqsListener(value = "${aws.sqs.queue.lowPriorityUrl}")
    public void receiveLowPriorityMessage(String message) {
        System.out.println("Received low priority message: " + message + Thread.currentThread().getName());
        sqsMessageProcessor.processMessage(message);
    }

}

