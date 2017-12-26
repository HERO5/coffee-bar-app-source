package com.example.apple.bakebar.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apple.bakebar.Entity.ConstantsHttp;
import com.example.apple.bakebar.MainActivity;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.HttpUtils;
import com.example.apple.bakebar.Util.JsonUtil;
import com.example.apple.bakebar.Util.PrefUtils;
import com.example.apple.bakebar.modle.UserMessage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by apple on 2017/11/1.
 */

public class SplashActivity extends AppCompatActivity {

    private ImageView iv_start;
    private UserMessage userMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //全屏代码
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        iv_start = (ImageView) findViewById(R.id.iv_start);
        init();
    }

    private void init() {
        userMessage = new UserMessage(UserMessage.LOGIN_ERROR,null);
        File dir = getFilesDir();
        final File imgFile = new File(dir, "start.png");
        if (imgFile.exists()) {
            iv_start.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            iv_start.setImageResource(R.mipmap.start);
        }
        iv_start.setImageResource(R.mipmap.start);
        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                String userId = PrefUtils.get("user", "userId", getApplicationContext());
                String userPwd = PrefUtils.get("user", "userPwd", getApplicationContext());
                if(HttpUtils.isNetworkConnected(SplashActivity.this)) {
                    if (userId != null && !userId.equals("")) {
                        //封装请求参数
                        RequestParams requestParams=new RequestParams();
                        requestParams.put("userId",userId);
                        requestParams.put("password",userPwd);
                        HttpUtils.post(ConstantsHttp.LOGIN, requestParams, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                userMessage = (UserMessage) JsonUtil.jsonToObject(new String(responseBody),UserMessage.class);
                                if(userMessage.getState()== UserMessage.LOGIN_SUCCESS){
                                    PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
                                    HttpUtils.client.setCookieStore(myCookieStore);
                                    List<Cookie> cookies = myCookieStore.getCookies();
                                    String JSESSIONID = "";
                                    if (cookies.isEmpty()) {
                                    } else {
                                        for (int i = 0; i < cookies.size(); i++) {
                                            if("JSESSIONID".equals(cookies.get(i).getName())){
                                                JSESSIONID = cookies.get(i).getValue();  // 第二种方法 通过 JSESSIONID
                                                break;
                                            }
                                        }
                                        PrefUtils.set("user","session",JSESSIONID,getBaseContext());
                                    }
                                    BasicClientCookie newCookie = new BasicClientCookie("userId",userMessage.getUser().getUserId());
                                    newCookie.setVersion(1);
                                    newCookie.setDomain("114.215.135.153");
                                    newCookie.setPath("/");
                                    myCookieStore.addCookie(newCookie);
                                    //保存用户id到本地
                                    PrefUtils.set("user","userId",userMessage.getUser().getUserId(),getBaseContext());
                                    PrefUtils.set("user","userPwd",userMessage.getUser().getUserPwd(),getBaseContext());
                                    PrefUtils.set("user","userPhone",userMessage.getUser().getUserPhone(),getBaseContext());
//                            PrefUtils.set("user","userInfo",JsonUtil.objectToJson(user),getBaseContext());
                                }else{
                                    Toast.makeText(SplashActivity.this, "Auto Login Fail", Toast.LENGTH_SHORT).show();
                                }
                                startMainActivity();
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(SplashActivity.this, "Service init,please re-in 20s later", Toast.LENGTH_LONG).show();
                                startMainActivity();
                            }
                        });
                    } else {
                        startLoginActivity();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Where Has The Internet Gone?",
                            Toast.LENGTH_SHORT).show();
                    startMainActivity();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_start.startAnimation(scaleAnim);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.putExtra("userInfo",userMessage);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("userInfo",userMessage);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    public void saveImage(File file, byte[] bytes) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
