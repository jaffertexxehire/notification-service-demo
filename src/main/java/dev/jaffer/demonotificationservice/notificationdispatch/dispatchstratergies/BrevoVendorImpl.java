package dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies;

import dev.jaffer.demonotificationservice.entity.User;
import dev.jaffer.demonotificationservice.notificationdispatch.DispatchPayload;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.ApiResponse;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class BrevoVendorImpl implements EmailVendorTransactional {

    @Value("${brevo.apiKey}")
    private String apiKey ;
    private static final String SENDER_EMAIL = "jafferhussain11@gmail.com";
    private static final String SENDER_NAME = "TexxeHire";

    private TransactionalEmailsApi api;


    public BrevoVendorImpl() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setApiKey(apiKey);
        defaultClient.addDefaultHeader("api-key", apiKey);
        api = new TransactionalEmailsApi(defaultClient);
    }

    @Override
    public void sendEmail(DispatchPayload dispatchPayload) {
        SendSmtpEmail email = new SendSmtpEmail();
        email.setSender(new SendSmtpEmailSender().email(SENDER_EMAIL).name(SENDER_NAME));
        email.setSubject("Hello from texxehire");
        email.setTemplateId(1L);
        email.setHtmlContent(dispatchPayload.getMessage());

        List<SendSmtpEmailTo> toList = new ArrayList<>();
        for (User user : dispatchPayload.getUserDetails()) {
            toList.add(new SendSmtpEmailTo().email(user.getEmail()).name(user.getName()));
        }
        email.setTo(toList);

        // Add any additional parameters from metadata
//        if (dispatchPayload.getMetadata() != null) {
//            email.setParams(dispatchPayload.getMetadata());
//        }

        try {
            CreateSmtpEmail result = api.sendTransacEmail(email);
            System.out.println("Email sent successfully. Message ID: " + result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TransactionalEmailsApi#sendTransacEmail");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

}
