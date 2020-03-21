package com.example.sharedpreferences;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.DrmInitData;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText accountEt,passwordEt;//账号、密码的输入框
    private Button saveBtn,showBtn;//保存、显示信息的按钮
    private SharedPreferences sharedPreferences;//共享参数
    private String username;//输入的用户名
    private String password;//输入的密码

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化操作
        accountEt=findViewById(R.id.accountEt);
        passwordEt= findViewById(R.id.passwordEt);
        saveBtn=findViewById(R.id.saveBtn);
        showBtn=findViewById(R.id.showBtn);

        //为保存、显示按钮设置监听事件
        saveBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.saveBtn:
                username=accountEt.getText().toString().trim();//获取用户名输入框中的内容，用trim()去掉首尾空格
                password=passwordEt.getText().toString().trim();
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password))
                {
                    Toast.makeText(MainActivity.this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();
                    Toast.makeText(MainActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.showBtn:
                sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
                username=sharedPreferences.getString("username","默认用户名");
                password=sharedPreferences.getString("password","默认密码");
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("显示保存的信息");
                builder.setMessage("用户名："+username+"\n"+"密码："+password);
                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();//调用create()方法时返回的是一个AlertDialog对象，再调用show()方法将对话框显示出来
                break;
            default:

        }

    }
}
