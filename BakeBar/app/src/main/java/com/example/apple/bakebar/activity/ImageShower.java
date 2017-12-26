package com.example.apple.bakebar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.bakebar.Entity.ConstantsHttp;
import com.example.apple.bakebar.Entity.Talk;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.HttpUtils;
import com.example.apple.bakebar.Util.JsonUtil;
import com.example.apple.bakebar.fragment.ImageLoadingDialog;
import com.example.apple.bakebar.modle.UserMessage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by apple on 2017/12/12.
 */

public class ImageShower extends AppCompatActivity {

    private Talk tal;
    private UserMessage userMessage;
    private String imgUrl;
    private FrameLayout imgContainer;
    private ImageView img;
    private LinearLayout talksArea;
    private TextView authorName;
    private TextView publishDate;
    public static ImageShower instance = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower);

        instance = this;
        Intent intent=getIntent();

        img=(ImageView)findViewById(R.id.img_detial);
        imgContainer = (FrameLayout) findViewById(R.id.img_container);
        talksArea = (LinearLayout)findViewById(R.id.talks_area);
        authorName = (TextView) findViewById(R.id.author_name);
        publishDate = (TextView) findViewById(R.id.publish_date);

        if(intent!=null){
//            Bitmap bitmap=intent.getParcelableExtra("bitmap");
//            img.setImageBitmap(bitmap);
            userMessage = (UserMessage) (intent.getSerializableExtra("userInfo"));
            Bundle bundle = this.getIntent().getExtras();
            imgUrl = bundle.getString("imgUrl");
            DisplayImageOptions userImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_stub)
                    .showImageForEmptyUri(R.mipmap.ic_empty)
                    .showImageOnFail(R.mipmap.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(imgUrl, img, userImageOptions);
            imgContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appendTalk();
                }
            });
            getTalks();
        }
        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.show();
        // 两秒后关闭后dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000 * 2);
    }

    private void appendTalk(){
        if(userMessage.getState()== UserMessage.LOGIN_ERROR){
            Toast.makeText(ImageShower.this, "You Have Not Login", Toast.LENGTH_LONG).show();
        }else {
            if(tal!=null){
                Intent intent = new Intent();
                intent.setClass(ImageShower.this, EditTalkActivity.class);
                intent.putExtra("userInfo", userMessage);
                intent.putExtra("talk", tal);
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl", imgUrl);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                Toast.makeText(ImageShower.this, "Some error happened!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void getTalks(){
        if (HttpUtils.isNetworkConnected(ImageShower.this)) {
            //封装请求参数
            long curTime=System.currentTimeMillis()/(1000*3600*24);
            RequestParams requestParams=new RequestParams();
            requestParams.put("date",curTime);
            requestParams.put("imgUrl",imgUrl);
            HttpUtils.post(ConstantsHttp.GETTALK, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Talk talk = new Talk();
                    List<Talk> talks = JsonUtil.jsonsToObjects(new String(responseBody),Talk.class);
                    initTalksArea(talks);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(ImageShower.this, "网络出现问题了", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(ImageShower.this, "没有网络连接!", Toast.LENGTH_LONG).show();
        }
    }

    private void initTalksArea(List<Talk> talks){
        if(talks!=null&&talks.size()>0){
            tal = talks.get(0);
            authorName.setText("Author Name:"+tal.getOwnerName());
            publishDate.setText("Publish Date:"+tal.getTalkDate());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for(Talk talk : talks){
                LinearLayout talkArea = new LinearLayout(this);
                talkArea.setOrientation(LinearLayout.VERTICAL);
                talkArea.setBackgroundResource(R.drawable.table_shape);
                TextView talkerName = new TextView(this);
                String name = talk.getTalkerName();
                if(name==null||"".equals(talk.getTalkerName())){
                    name=talk.getTalkerId();
                }
                talkerName.setText("Talker:"+name);
                TextView talkDate = new TextView(this);
                talkDate.setText("Date  :"+format.format(talk.getTalkDate()));
                TextView talkBody = new TextView(this);
                talkBody.setText("Content:"+talk.getTalkBody());
                talkArea.addView(talkerName);
                talkArea.addView(talkDate);
                talkArea.addView(talkBody);
                talksArea.addView(talkArea);
            }
        }else{
            Toast.makeText(ImageShower.this, "Some error happened!", Toast.LENGTH_LONG).show();
        }
    }
}
