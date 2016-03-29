package com.example.wallace.myapplication.biz;

/**
 * Created by Wallace on 2015/12/23.
 */
public interface IUserBiz {
    void login(String username, String password, OnLoginListener loginListener);
}
