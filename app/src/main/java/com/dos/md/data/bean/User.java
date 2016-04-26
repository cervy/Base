package com.dos.md.data.bean;

/**
 * Created by DOS on 2015/12/30.
 */
public class User {
    public String userName;
    public String userId;
    public String userUrl;

    public int type;

    public User(String userName, String userUrl, String userId, int type) {
        this.userName = userName;
        this.userId = userId;
        this.userUrl = userUrl;
        this.type = type;
    }

    public User(int type) {
        this.type = type;
    }


}
