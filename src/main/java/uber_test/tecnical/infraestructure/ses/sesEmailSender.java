package uber_test.tecnical.infraestructure.ses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import uber_test.tecnical.adapters.EmailSenderGateway;
import uber_test.tecnical.core.exceptions.EmailServiceException;

@Service
public class sesEmailSender implements EmailSenderGateway {

    private final AmazonSimpleEmailService amazonSimpleEmailService;


    @Autowired
    public sesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        
        SendEmailRequest request = new SendEmailRequest().withSource("mateussoftwaredeveloper@gmaail.com")
        .withDestination(new Destination().withToAddresses(to))
        .withMessage(new Message().withSubject(new Content(subject)).withBody(new Body().withText(new Content(body))));


        try{

            this.amazonSimpleEmailService.sendEmail(request);


        }catch(AmazonServiceException e){
            throw new EmailServiceException("Failed to send email", e);

        }
            
    }
    
}
