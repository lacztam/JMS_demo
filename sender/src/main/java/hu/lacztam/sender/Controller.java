package hu.lacztam.sender;

import hu.lacztam.model_lib.AccountModel;
import hu.lacztam.model_lib.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@RestController
@RequestMapping("/send")
public class Controller {

    @Autowired
    JmsTemplate jmsTemplate;

    private final SimpleMessageConverter converter = new SimpleMessageConverter();

    @PostMapping("/account")
    public void sendJmsMessage(){
        AccountModel acc1 = new AccountModel("Tamás","pass1234","e@email.com");

        this.jmsTemplate.convertAndSend("accounts", acc1);
        System.out.println("account send.");
    }

    @PostMapping("/user")
    @Transactional
    public void sendAndReceiveJmsMessage() throws JMSException {

        Object textMsg = this.jmsTemplate.sendAndReceive("users", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("akita-kutya");
                return message;
            }
        });

        System.out.println("sender received: " + this.converter.fromMessage((Message) textMsg));
    }

}
