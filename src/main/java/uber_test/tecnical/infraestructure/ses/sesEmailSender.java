package uber_test.tecnical.infraestructure.ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;

import uber_test.tecnical.adapters.EmailSenderGateway;

public class sesEmailSender implements EmailSenderGateway {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public sesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        
        throw new UnsupportedOperationException("Unimplemented method 'sendEmail'");
    }
    
}
