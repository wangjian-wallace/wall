package com.example.wallace.myapplication.presenter;

import android.os.Handler;

import com.example.wallace.myapplication.bean.User;
import com.example.wallace.myapplication.biz.IUserBiz;
import com.example.wallace.myapplication.biz.OnLoginListener;
import com.example.wallace.myapplication.biz.UserBiz;
import com.example.wallace.myapplication.view.ILoginView;

/**
 * Created by Wallace on 2015/12/23.
 */
public class UserLoginPresenter {
    private IUserBiz userBiz;
    private ILoginView loginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(ILoginView loginView)
    {
        this.loginView = loginView;
        this.userBiz = new UserBiz();
    }

    public void login()
    {
        loginView.showLoading();
        userBiz.login(loginView.getUserName(), loginView.getPassword(), new OnLoginListener()
        {
            @Override
            public void loginSuccess(final User user)
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loginView.toMainActivity(user);
                        loginView.hideLoading();
                    }
                });

            }

            @Override
            public void loginFailed()
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loginView.showFailedError();
                        loginView.hideLoading();
                    }
                });

            }
        });
    }

    public void clear()
    {
        loginView.clearUserName();
        loginView.clearPassword();
    }
}
