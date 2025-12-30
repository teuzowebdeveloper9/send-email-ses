package uber_test.tecnical.aplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uber_test.tecnical.adapters.EmailSenderGateway;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {

    @Mock
    private EmailSenderGateway emailSenderGateway;

    private EmailSenderService emailSenderService;

    @BeforeEach
    void setUp() {
        emailSenderService = new EmailSenderService(emailSenderGateway);
    }

    @Test
    void delegatesSendToGateway() {
        String to = "to@example.com";
        String subject = "Hello";
        String body = "Test message";

        emailSenderService.SendEmail(to, subject, body);

        verify(emailSenderGateway).sendEmail(to, subject, body);
    }
}

