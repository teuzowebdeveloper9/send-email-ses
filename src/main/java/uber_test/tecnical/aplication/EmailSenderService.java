package uber_test.tecnical.aplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uber_test.tecnical.adapters.EmailSenderGateway;
import uber_test.tecnical.core.emailSenderUseCase;

@Service
public class EmailSenderService implements emailSenderUseCase {


    private final EmailSenderGateway  emailSenderGateway;


    @Autowired
    public EmailSenderService(EmailSenderGateway emailSenderGateway) {
        this.emailSenderGateway = emailSenderGateway;
    }

    @Override
    public void SendEmail(String to, String subject, String body) {
       this.emailSenderGateway.sendEmail(to, subject, body);
    }

}
