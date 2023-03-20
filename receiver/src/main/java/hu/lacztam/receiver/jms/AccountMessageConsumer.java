package hu.lacztam.receiver.jms;

import hu.lacztam.model_lib.AccountModel;
import hu.lacztam.model_lib.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class AccountMessageConsumer {
    @Autowired
    JmsTemplate jmsTemplate;

    private final SimpleMessageConverter converter = new SimpleMessageConverter();

    @JmsListener(destination = "accounts")
    public void onAccountMessage(AccountModel accountModel){
        System.out.println("Receiver-accounts/account.toString()" + accountModel.toString());

    }

    @JmsListener(destination = "users")
    public void onUsertMessage(final Message message) throws JMSException {
        System.out.println("received users, message: " + this.converter.fromMessage(message));

        jmsTemplate.send(message.getJMSReplyTo(), new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                Message responseMsg = session.createTextMessage("received-akita-kutya");
                responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                return responseMsg;
            }
        });

        this.jmsTemplate.convertAndSend(message.getJMSReplyTo(),new UserModel("u", "e", "pw"));
        System.out.println("receiver send UserModel");
    }

}
