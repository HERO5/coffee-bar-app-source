package com.example.apple.bakebar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.apple.bakebar.R;
import com.example.apple.bakebar.adapter.XGalleryAdapter;
import com.xgallery.android.XGallery;

/**
 * Created by apple on 2017/10/31.
 */

public class XGalleryViewActivity extends AppCompatActivity implements XGallery.OnGalleryPageSelectListener{

    private XGallery xGallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_view);

//        xGallery = (XGallery) findViewById(R.id.xgallery);
        xGallery.setAdapter(new XGalleryAdapter());
        xGallery.setOnGalleryPageSelectListener(this);
    }

    @Override
    public void onGalleryPageSelected(int position) {
        Toast.makeText(this, "selected item: " + position, Toast.LENGTH_SHORT).show();
    }
}
