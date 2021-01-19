package client.Instances;

import shared.message.User;

public class AuthMessage extends Message {
    private User user;
    private boolean isRegister;
    public AuthMessage(User user, boolean isRegister){
        super("Говна поешь");
        this.isRegister=isRegister;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public boolean getBoolean() {
        return isRegister;
    }
}
