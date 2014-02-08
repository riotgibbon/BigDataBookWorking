package com.bigdata.c3;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 04/02/14
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class Login {
    public String userName;
    public long loginUnixTime;

    public Login (String _userName, long _loginUnixTime){
        userName=_userName;
        loginUnixTime = _loginUnixTime;
    }
}
