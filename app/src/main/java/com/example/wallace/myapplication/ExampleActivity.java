package com.example.wallace.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wallace.myapplication.bean.User;
import com.example.wallace.myapplication.presenter.UserLoginPresenter;
import com.example.wallace.myapplication.view.ILoginView;

/**
 * MVP设计模式的简单示例
 */
public class ExampleActivity extends Activity implements ILoginView{

    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin, mBtnClear;
    private ProgressBar mPbLoading;

    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        initViews();
    }

    private void initViews()
    {
        mEtUsername = (EditText) findViewById(R.id.etName);
        mEtPassword = (EditText) findViewById(R.id.etPassword);

        mBtnClear = (Button) findViewById(R.id.btnClear);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);

        mPbLoading = (ProgressBar) findViewById(R.id.progressBar);

        mBtnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUserLoginPresenter.login();
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUserLoginPresenter.clear();
            }
        });
    }


    @Override
    public String getUserName()
    {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword()
    {
        return mEtPassword.getText().toString();
    }

    @Override
    public void clearUserName()
    {
        mEtUsername.setText("");
    }

    @Override
    public void clearPassword()
    {
        mEtPassword.setText("");
    }

    @Override
    public void showLoading()
    {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
        mPbLoading.setVisibility(View.INVISIBLE);
    }


    @Override
    public void toMainActivity(User user)
    {
//        Toast.makeText(this, user.getUsername() +
//                " login success , to MainActivity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,TransformersActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedError()
    {
        Toast.makeText(this,
                "login failed", Toast.LENGTH_SHORT).show();
    }
}
