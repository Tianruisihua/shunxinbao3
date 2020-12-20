package com.example.shunxinbao3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallPhone extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private EditText number;
    private String phoneNum;
    private int changeType;
    private Button change,cancle,query;
    private String myNum = "";
    private TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        number = findViewById(R.id.number);
        textView1 = findViewById(R.id.ttt);
        change= findViewById(R.id.change);
        change.setOnClickListener(this);
        cancle= findViewById(R.id.cancle);
        cancle.setOnClickListener(this);
        query= findViewById(R.id.query);
        query.setOnClickListener(this);

        Intent intent2 = getIntent();
        myNum = intent2.getStringExtra("name");
        textView1.setText(myNum);


    }


    //点击返回键返回桌面而不是退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        phoneNum=number.getText().toString().trim();

     //   myNum =myNumber.getText().toString().trim();
        Intent intent=null;
        switch (v.getId()) {
            case R.id.change:    //转移
                if(phoneNum!=null&&phoneNum.length()>0){
                    if(IsValidMobileNo(phoneNum)){
                        intent=new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        if(changeType==0){
                            intent.setData(Uri.parse("tel:**21*"+phoneNum+"%23"));  //始终进行呼叫转移
                        }else if(changeType==1){
                            intent.setData(Uri.parse("tel:**67*"+phoneNum+"%23"));  //占线时进行呼叫转移
                        }else if(changeType==2){
                            intent.setData(Uri.parse("tel:**61*"+phoneNum+"%23"));  //无应答时进行呼叫转移
                        }else if(changeType==3){
                            intent.setData(Uri.parse("tel:**62*"+phoneNum+"%23"));  //无法接通时进行呼叫转移
                        }
                        startActivity(intent);
                        Toast.makeText(this,R.string.app_name11, Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //要延时的程序
                                number.setText("");
                                call(myNum);

                            }
                        },4000); //8000为毫秒单位

                    }else{
                        Toast.makeText(this, getString(R.string.app_name08), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, getString(R.string.app_name07), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancle:   //取消
                intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                if(changeType==0){
                    intent.setData(Uri.parse("tel:%23%2321%23"));
                }else if(changeType==1){
                    intent.setData(Uri.parse("tel:%23%2367%23"));
                }else if(changeType==2){
                    intent.setData(Uri.parse("tel:%23%2361%23"));
                }else if(changeType==3){
                    intent.setData(Uri.parse("tel:%23%2362%23"));
                }
                Toast.makeText(this, getString(R.string.app_name10), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.query:   //查询
                intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                if(changeType==0){
                    intent.setData(Uri.parse("tel:*%2321%23"));
                }else if(changeType==1){
                    intent.setData(Uri.parse("tel:*%2367%23"));
                }else if(changeType==2){
                    intent.setData(Uri.parse("tel:*%2361%23"));
                }else if(changeType==3){
                    intent.setData(Uri.parse("tel:*%2362%23"));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(this, getString(R.string.app_name09), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
    }
    public static boolean IsValidMobileNo(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /**
     * 调用拨号功能
     * @param phone 电话号码
     */
    private void call(String phone) {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");//调用android自带的拨号Activity
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);//启动系统中的任何Acitivity
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        changeType=position;
        System.out.println("------"+changeType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}