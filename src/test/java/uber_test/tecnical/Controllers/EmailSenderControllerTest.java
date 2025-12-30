package uber_test.tecnical.Controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import uber_test.tecnical.aplication.EmailSenderService;
import uber_test.tecnical.core.exceptions.EmailServiceException;

@ExtendWith(MockitoExtension.class)
class EmailSenderControllerTest {

    @Mock
    private EmailSenderService emailSenderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EmailSenderController(emailSenderService)).build();
    }

    @Test
    void returnsOkWhenEmailSent() throws Exception {
        String payload = """
                {
                  "To": "dest@example.com",
                  "Subject": "Hello",
                  "Body": "Test"
                }
                """;

        mockMvc.perform(post("/api/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().string("email sent successfully"));

        verify(emailSenderService).SendEmail(eq("dest@example.com"), eq("Hello"), eq("Test"));
    }

    @Test
    void returnsServerErrorWhenGatewayFails() throws Exception {
        String payload = """
                {
                  "To": "dest@example.com",
                  "Subject": "Hello",
                  "Body": "Test"
                }
                """;

        doThrow(new EmailServiceException("fail"))
                .when(emailSenderService)
                .SendEmail(eq("dest@example.com"), eq("Hello"), eq("Test"));

        mockMvc.perform(post("/api/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error while sending email"));
    }
}

