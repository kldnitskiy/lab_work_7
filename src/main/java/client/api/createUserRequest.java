package client.api;

import shared.message.Message;
import shared.message.User;

public class createUserRequest extends Message {
    private User user;
    private boolean isRegister;
    public createUserRequest(User user, boolean isRegister){
        super("createdUserInstance");
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
