package hu.lacztam.sender;

import hu.lacztam.model_lib.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class Controller {

    @Autowired
    JmsTemplate jmsTemplate;

    @PostMapping("/account")
    public void sendJmsMessage(){
        AccountModel acc1 = new AccountModel("Tamás","pass1234","tamas@email.com");

        this.jmsTemplate.convertAndSend("accounts", acc1);
        System.out.println("account send.");
    }

}
