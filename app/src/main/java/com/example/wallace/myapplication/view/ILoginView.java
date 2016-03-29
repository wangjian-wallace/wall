package com.example.wallace.myapplication.view;

import com.example.wallace.myapplication.bean.User;

/**
 * Created by Wallace on 2015/12/23.
 */
public interface ILoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
