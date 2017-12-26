package com.example.apple.bakebar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apple.bakebar.Entity.ConstantsHttp;
import com.example.apple.bakebar.Entity.Talk;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.HttpUtils;
import com.example.apple.bakebar.Util.JsonUtil;
import com.example.apple.bakebar.modle.UserMessage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by apple on 2017/12/14.
 */

public class EditTalkActivity extends AppCompatActivity {

    private EditText text;
    private Button submit;
    private UserMessage userMessage;
    private String imgUrl;
    private Talk tal;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent!=null){
            userMessage = (UserMessage) intent.getSerializableExtra("userInfo");
            tal = (Talk) intent.getSerializableExtra("talk");
            Bundle bundle = this.getIntent().getExtras();
            imgUrl = bundle.getString("imgUrl");
        }
        setContentView(R.layout.edit_talk);

        text = (EditText) findViewById(R.id.edit_area);
        submit = (Button) findViewById(R.id.btn_submit);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.getText()==null){
                    text.setError("Content can not be null");
                }else{
                    tal.setTalkId(null);
                    tal.setTalkerId(userMessage.getUser().getUserId());
                    tal.setTalkerName(userMessage.getUser().getUserName());
                    tal.setTalkBody(text.getText().toString());
                    tal.setTalkDate(new Date());
                    submitTalk(tal);
                }
            }
        });
    }

    private void submitTalk(Talk talk){
        progressBar.setVisibility(View.VISIBLE);
        if (HttpUtils.isNetworkConnected(EditTalkActivity.this)) {
            String talkJson = JsonUtil.objectToJson(talk);
            //封装请求参数
            RequestParams requestParams = new RequestParams();
            requestParams.put("talk", talkJson);

            HttpUtils.postWithAuth(getBaseContext(), ConstantsHttp.ADDTALK, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(responseBody!=null&&!"".equals(new String(responseBody))){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditTalkActivity.this, "Submit Succeed", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditTalkActivity.this, ImageShower.class);
                        intent.putExtra("userInfo", userMessage);
                        Bundle bundle = new Bundle();
                        bundle.putString("imgUrl", imgUrl);
                        intent.putExtras(bundle);
                        ImageShower.instance.finish();
                        startActivity(intent);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditTalkActivity.this, "Submit Failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditTalkActivity.this, "服务器繁忙", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "没有网络连接!", Toast.LENGTH_LONG).show();
        }
    }
}
