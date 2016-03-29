package com.example.wallace.myapplication.biz;

import com.example.wallace.myapplication.bean.User;

/**
 * Created by Wallace on 2015/12/23.
 */
public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed();
}
