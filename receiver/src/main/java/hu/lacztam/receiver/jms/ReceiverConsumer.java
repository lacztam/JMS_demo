package hu.lacztam.receiver.jms;

import hu.lacztam.model_lib.AccountModel;
import hu.lacztam.model_lib.TestObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class ReceiverConsumer {
    @Autowired
    JmsTemplate jmsTemplate;

    private final SimpleMessageConverter converter = new SimpleMessageConverter();

    @JmsListener(destination = "accounts")
    public void onAccountMessage(AccountModel accountModel){
        System.out.println("Receiver-accounts/account.toString()" + accountModel.toString());

    }

    @JmsListener(destination = "users")
    public void onUsertMessage(final Message message) throws JMSException {

        TestObj to = (TestObj) ((ObjectMessage)message).getObject();
        System.out.println("to.toString(): " + to.toString());

        jmsTemplate.send(message.getJMSReplyTo(), new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(to);
            }
        });
        System.out.println("msg received and send");
    }

    //was original
    @JmsListener(destination = "users2")
    public void onUsertMessage2(final Message message) throws JMSException {
        System.out.println("received users, message: " + this.converter.fromMessage(message));

        jmsTemplate.send(message.getJMSReplyTo(), new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                Message responseMsg = session.createTextMessage("received-akita-kutya");
                responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                return responseMsg;
            }
        });
        System.out.println("msg received and send");
    }

}
