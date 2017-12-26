package com.example.apple.bakebar.modle;

import java.io.Serializable;

/**
 * Created by apple on 2017/11/2.
 */

public class UserMessage implements Serializable{

    public static final int REGISTER_SUCCESS = 1;
    public static final int REGISTER_ERROR_NICK = 21;
    public static final int REGISTER_ERROR_PHONE = 22;
    public static final int REGISTER_ERROR = 2;
    public static final int LOGIN_SUCCESS = 3;
    public static final int LOGIN_ERROR = 4;
    public static final int UPDATE_SUCCEED = 5;
    public static final int UPDATE_ERROR = 6;
    private int state;
    private User user;
    public UserMessage(){

    }
    public UserMessage(int state,User user){
        this.state = state;
        this.user = user;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "state=" + state +
                ", user=" + user +
                '}';
    }
}
