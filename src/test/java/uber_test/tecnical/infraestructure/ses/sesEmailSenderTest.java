package uber_test.tecnical.infraestructure.ses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import uber_test.tecnical.core.exceptions.EmailServiceException;

@ExtendWith(MockitoExtension.class)
class sesEmailSenderTest {

    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;

    private sesEmailSender emailSender;

    @BeforeEach
    void setUp() {
        emailSender = new sesEmailSender(amazonSimpleEmailService);
    }

    @Test
    void sendsEmailWithExpectedRequest() {
        String to = "dest@example.com";
        String subject = "Hi";
        String body = "Body";

        emailSender.sendEmail(to, subject, body);

        ArgumentCaptor<SendEmailRequest> captor = ArgumentCaptor.forClass(SendEmailRequest.class);
        verify(amazonSimpleEmailService).sendEmail(captor.capture());

        SendEmailRequest request = captor.getValue();
        assertEquals("mateussoftwaredeveloper@gmaail.com", request.getSource());
        assertEquals(List.of(to), request.getDestination().getToAddresses());

        Message message = request.getMessage();
        Content subjectContent = message.getSubject();
        Body requestBody = message.getBody();

        assertEquals(subject, subjectContent.getData());
        assertEquals(body, requestBody.getText().getData());
    }

    @Test
    void wrapsAmazonServiceException() {
        doThrow(new AmazonServiceException("fail")).when(amazonSimpleEmailService).sendEmail(any(SendEmailRequest.class));

        assertThrows(EmailServiceException.class, () ->
                emailSender.sendEmail("to@example.com", "Subject", "Body")
        );
    }
}

