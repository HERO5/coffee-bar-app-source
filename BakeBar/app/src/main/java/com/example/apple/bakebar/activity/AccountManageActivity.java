package com.example.apple.bakebar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apple.bakebar.Entity.ConstantsHttp;
import com.example.apple.bakebar.MainActivity;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.HttpUtils;
import com.example.apple.bakebar.Util.JsonUtil;
import com.example.apple.bakebar.Util.PrefUtils;
import com.example.apple.bakebar.Util.SetUpOnClickListener;
import com.example.apple.bakebar.modle.User;
import com.example.apple.bakebar.modle.UserMessage;
import com.example.apple.bakebar.view.CircleImageView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by apple on 2017/11/2.
 */

public class AccountManageActivity extends AppCompatActivity{

    private UserMessage userMessage;
    private CircleImageView avatorView;
    private Button selectAvatorView;
    private Button submit;
    private Button outLogin;
    private EditText nicknameView;
    private EditText passwordView;
    private EditText confirmView;
    private EditText emailView;
    private EditText qqView;
    private EditText addressView;
    private EditText homeView;
    private ProgressBar progressBar;

    private List<String> paths;

    private static int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Edit Account Info");

        avatorView = (CircleImageView) findViewById(R.id.user_avator);
        selectAvatorView = (Button) findViewById(R.id.btn_select_avator);
        submit = (Button) findViewById(R.id.btn_submit);
        outLogin = (Button) findViewById(R.id.btn_outLogin);
        nicknameView = (EditText) findViewById(R.id.nickname_et);
        passwordView = (EditText) findViewById(R.id.password_et);
        confirmView = (EditText) findViewById(R.id.re_password_et);
        emailView = (EditText) findViewById(R.id.email_et);
        qqView = (EditText) findViewById(R.id.qq_et);
        addressView = (EditText) findViewById(R.id.address_et);
        homeView = (EditText) findViewById(R.id.hometown_et);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        userMessage = (UserMessage) getIntent().getSerializableExtra("userInfo");

        showUserInfo();
        selectAvatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paths = null;
                MultiImageSelector.create(AccountManageActivity.this)
                        .showCamera(true) // show camera or not. true by default
                        .single() // single mode
                        .start(AccountManageActivity.this, REQUEST_CODE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyUserInfo();
            }
        });
        outLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.remove("user","userId",getBaseContext());
                PrefUtils.remove("user","userPwd",getBaseContext());
                PrefUtils.remove("user","userPhone",getBaseContext());
                userMessage.setState(UserMessage.LOGIN_ERROR);
                userMessage.setUser(null);
                Toast.makeText(AccountManageActivity.this, "Out Login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AccountManageActivity.this, LoginActivity.class);
                intent.putExtra("userInfo", userMessage);
                MainActivity.instance.finish();
                startActivity(intent);
                finish();
            }
        });
        avatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paths = null;
                MultiImageSelector.create(AccountManageActivity.this)
                        .showCamera(true) // show camera or not. true by default
                        .single() // single mode
                        .start(AccountManageActivity.this, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the result list of select image paths
                paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Bitmap bmImg = BitmapFactory.decodeFile(paths.get(0));
                avatorView.setImageBitmap(bmImg);
            }
        }
    }

    private void modifyUserInfo() {

        boolean cancel = false;
        View focusView = null;

        progressBar.setVisibility(View.VISIBLE);
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordView.getText().toString()) && !isPasswordValid(passwordView.getText().toString())) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(nicknameView.getText().toString())) {
            nicknameView.setError("昵称不能为空");
            focusView = nicknameView;
            cancel = true;
        } else if (!isNicknameValid(nicknameView.getText().toString())) {
            nicknameView.setError("昵称太短了");
            focusView = nicknameView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(confirmView.getText().toString())) {
            confirmView.setError("重复密码不能为空");
            focusView = confirmView;
            cancel = true;
        } else if (!isPasswordAgainValid(confirmView.getText().toString(),passwordView.getText().toString())) {
            confirmView.setError("两次密码不一致");
            focusView = confirmView;
            cancel = true;
        }

        if (paths == null) {
            Toast.makeText(AccountManageActivity.this, "头像不能为空", Toast.LENGTH_SHORT).show();
            focusView = avatorView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        File file = new File(paths.get(0));
        if(file.length()>(1024*1024*10)){
            Toast.makeText(AccountManageActivity.this, "Your File Is Too Large", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (HttpUtils.isNetworkConnected(this)) {
            User user = userMessage.getUser();
            user.setUserName(nicknameView.getText().toString());
            user.setUserPwd(passwordView.getText().toString());
            user.setUserMail(emailView.getText().toString());
            user.setUserQq(qqView.getText().toString());
            user.setUserAddress(addressView.getText().toString());
            user.setUserHometown(homeView.getText().toString());
            String userJson = JsonUtil.objectToJson(user);
            //封装请求参数
            RequestParams requestParams = new RequestParams();
            requestParams.put("userInfo", userJson);
            try {
                requestParams.put("file", file, "image/*");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "图片上传失败" + e, Toast.LENGTH_LONG).show();
            }

            HttpUtils.postWithAuth(getBaseContext(), ConstantsHttp.UPDATE, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<JsonResult<User>>() {
//                    }.getType();
//                    JsonResult<User> jsonResult = gson.fromJson(new String(responseBody), type);
//                    String status = jsonResult.getStatus();
//                    User user = jsonResult.getData();
                    //Log.v(TAG,data);
                    userMessage = (UserMessage) JsonUtil.jsonToObject(new String(responseBody),UserMessage.class);
                    if(
//                                TextUtils.equals(status, Result.SUCCESS)&&
                            userMessage.getState()== UserMessage.UPDATE_SUCCEED){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AccountManageActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AccountManageActivity.this, MainActivity.class);
                        intent.putExtra("userInfo", userMessage);
                        MainActivity.instance.finish();
                        startActivity(intent);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AccountManageActivity.this, "请重新登录", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AccountManageActivity.this, "服务器繁忙", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "没有网络连接!", Toast.LENGTH_LONG).show();
        }
    }

    private void showUserInfo() {
        List<ImageView> imgs = new ArrayList<ImageView>();
        imgs.add(avatorView);
        SetUpOnClickListener.loadImgArray(imgs, new String[]{ConstantsHttp.BASEURL+ConstantsHttp.BASEURL_IMG+userMessage.getUser().getUserImg()});
        nicknameView.setText(userMessage.getUser().getUserName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            case R.id.action_done:
                progressBar.setVisibility(View.VISIBLE);
                modifyUserInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.modifiy_menu, menu);
        return true;
    }

    private boolean isNicknameValid(String nickname) {
        //TODO: Replace this with your own logic
        return nickname.length()>2;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    private boolean isPasswordAgainValid(String passwordAgain,String password) {
        //TODO: Replace this with your own logic
        return passwordAgain.equals(password);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
