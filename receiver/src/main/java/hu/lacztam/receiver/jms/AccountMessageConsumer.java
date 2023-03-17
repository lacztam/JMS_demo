package hu.lacztam.receiver.jms;

import hu.lacztam.model_lib.AccountModel;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AccountMessageConsumer {

    @JmsListener(destination = "accounts")
    public void onAccountMessage(AccountModel accountModel){
        System.out.println("Receiver/account.toString()" + accountModel.toString());

    }
}
