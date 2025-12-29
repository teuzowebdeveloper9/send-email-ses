package uber_test.tecnical.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uber_test.tecnical.aplication.EmailSenderService;
import uber_test.tecnical.core.DTOs.EmailSenderDTO;
import uber_test.tecnical.core.exceptions.EmailServiceException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/email")
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }


    @PostMapping("/send")
    public ResponseEntity<String> SendEmail(@RequestBody EmailSenderDTO request ) {
        try{

            this.emailSenderService.SendEmail(request.To() , request.Subject() , request.Body());

            return ResponseEntity.ok("email sent successfully");

            
        }catch(EmailServiceException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending email");
        }
    }
  
    

}
