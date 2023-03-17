package hu.lacztam.receiver.jms;

import hu.lacztam.sender.model.Account;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AccountMessageConsumer {

    @JmsListener(destination = "accounts")
    public void onAccountMessage(Account account){
        System.out.println("Receiver/account.toString()" + account.toString());

    }
}
