package com.yn.tools.netty.msg;

import java.io.Serializable;

/**
 * Created by yangnan on 16/12/12.
 */

public class User implements Serializable {

    private String userName;
    private int userId;

    public User() {
    }

    public User(String userName, int userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
