package com.example.apple.bakebar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.apple.bakebar.Entity.ConstantsImages;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.SetUpOnClickListener;
import com.example.apple.bakebar.activity.CallSystemCamera;
import com.example.apple.bakebar.activity.ImageSelectActivity;
import com.example.apple.bakebar.modle.UserMessage;
import com.example.apple.bakebar.view.CriclesView;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/10/26.
 */

public class BakeStudioFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener {

    private final String TAG = "BlankFragment";

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private FloatingSearchView mSearchView;
    private AppBarLayout mAppBar;
    private TextView view_more_works1;
    private TextView view_more_works2;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";

    private List<ImageView> images;

    private ImageView naive;
    private ImageView advanced;
    private ImageView master;

    private FloatingActionButton img1_watch;
    private FloatingActionButton img1_collect;
    private FloatingActionButton img1_detial;
    private FloatingActionButton img2_watch;
    private FloatingActionButton img2_collect;
    private FloatingActionButton img2_detial;
    private FloatingActionButton img3_watch;
    private FloatingActionButton img3_collect;
    private FloatingActionButton img3_detial;
    private FloatingActionButton img4_collect;
    private FloatingActionButton img4_detial;
    private FloatingActionButton img5_collect;
    private FloatingActionButton img5_detial;
    private FloatingActionButton img6_collect;
    private FloatingActionButton img6_detial;
    private FloatingActionButton img7_collect;
    private FloatingActionButton img7_detial;

    private UserMessage userMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bake_studio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        mAppBar = (AppBarLayout) view.findViewById(R.id.appbar);

        mAppBar.addOnOffsetChangedListener(this);

        view_more_works1 = (TextView)view.findViewById(R.id.view_more_studio1);
        view_more_works2 = (TextView)view.findViewById(R.id.view_more_studio2);

        images = new ArrayList<>();

        images.add((ImageView)view.findViewById(R.id.img1));
        images.add((ImageView)view.findViewById(R.id.img2));
        images.add((ImageView)view.findViewById(R.id.img3));
        images.add((ImageView)view.findViewById(R.id.img4));
        images.add((ImageView)view.findViewById(R.id.img5));
        images.add((ImageView)view.findViewById(R.id.img6));
        images.add((ImageView)view.findViewById(R.id.img7));

        naive = (ImageView) view.findViewById(R.id.naive);
        advanced = (ImageView) view.findViewById(R.id.advanced);
        master = (ImageView) view.findViewById(R.id.master);

        img1_watch = (FloatingActionButton) view.findViewById(R.id.img1_watch);
        img1_collect = (FloatingActionButton) view.findViewById(R.id.img1_collect);
        img1_detial = (FloatingActionButton) view.findViewById(R.id.img1_detial);
        img2_watch = (FloatingActionButton) view.findViewById(R.id.img2_watch);
        img2_collect = (FloatingActionButton) view.findViewById(R.id.img2_collect);
        img2_detial = (FloatingActionButton) view.findViewById(R.id.img2_detial);
        img3_watch = (FloatingActionButton) view.findViewById(R.id.img3_watch);
        img3_collect = (FloatingActionButton) view.findViewById(R.id.img3_collect);
        img3_detial = (FloatingActionButton) view.findViewById(R.id.img3_detial);
        img4_collect = (FloatingActionButton) view.findViewById(R.id.img4_collect);
        img4_detial = (FloatingActionButton) view.findViewById(R.id.img4_detial);
        img5_collect = (FloatingActionButton) view.findViewById(R.id.img5_collect);
        img5_detial = (FloatingActionButton) view.findViewById(R.id.img5_detial);
        img6_collect = (FloatingActionButton) view.findViewById(R.id.img6_collect);
        img6_detial = (FloatingActionButton) view.findViewById(R.id.img6_detial);
        img7_collect = (FloatingActionButton) view.findViewById(R.id.img7_collect);
        img7_detial = (FloatingActionButton) view.findViewById(R.id.img7_detial);

        if(getActivity().getIntent().getSerializableExtra("userInfo")!=null){
            userMessage = (UserMessage) (getActivity().getIntent().getSerializableExtra("userInfo"));
        }

        setupDrawer();
        setupSearchBar(view);
        setupCustomView(view);
        setupImgOnClick();
        setupViewMore();
        setTypeOnClick();
        loadImg();
    }

    @Override
    public boolean onActivityBackPress() {
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
        if (!mSearchView.setSearchFocused(false)) {
            return false;
        }
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mSearchView.setTranslationY(verticalOffset);
    }

    private void setupSearchBar(final View view){

        //handle menu clicks the same way as you would
        //in a regular activity
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                Intent intent = new Intent();
                switch (item.getItemId()){
                    case R.id.search_menu:
                        String queryStr = mSearchView.getQuery().toString();
                        SetUpOnClickListener.doQuery(view,queryStr,getActivity(),userMessage);
                        break;
                    case R.id.call_camera:
                        Toast.makeText(getActivity().getApplicationContext(), item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        intent.setClass(view.getContext(), CallSystemCamera.class);
                        //第一个参数就是获取当前的context。在activity类中可以用CurrentActivity.this代替
                        //这里第二个参数是将跳到的activity
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.upload_img:
                        intent.setClass(view.getContext(), ImageSelectActivity.class);
                        intent.putExtra("userInfo", userMessage);
                        view.getContext().startActivity(intent);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void setupImgOnClick(){
        Activity activity = getActivity();

        SetUpOnClickListener.setImgWatchListener(img1_watch, activity);
        SetUpOnClickListener.setImgWatchListener(img2_watch, activity);
        SetUpOnClickListener.setImgWatchListener(img3_watch, activity);

        SetUpOnClickListener.setImgCollectListener(img1_collect, ConstantsImages.BAKE_STUDIO[0], activity);
        SetUpOnClickListener.setImgCollectListener(img2_collect, ConstantsImages.BAKE_STUDIO[1], activity);
        SetUpOnClickListener.setImgCollectListener(img3_collect, ConstantsImages.BAKE_STUDIO[2], activity);
        SetUpOnClickListener.setImgCollectListener(img4_collect, ConstantsImages.BAKE_STUDIO[3], activity);
        SetUpOnClickListener.setImgCollectListener(img5_collect, ConstantsImages.BAKE_STUDIO[4], activity);
        SetUpOnClickListener.setImgCollectListener(img6_collect, ConstantsImages.BAKE_STUDIO[5], activity);
        SetUpOnClickListener.setImgCollectListener(img7_collect, ConstantsImages.BAKE_STUDIO[6], activity);

        SetUpOnClickListener.setImgDetialListener(img1_detial, ConstantsImages.BAKE_STUDIO[0], userMessage);
        SetUpOnClickListener.setImgDetialListener(img2_detial, ConstantsImages.BAKE_STUDIO[1], userMessage);
        SetUpOnClickListener.setImgDetialListener(img3_detial, ConstantsImages.BAKE_STUDIO[2], userMessage);
        SetUpOnClickListener.setImgDetialListener(img4_detial, ConstantsImages.BAKE_STUDIO[3], userMessage);
        SetUpOnClickListener.setImgDetialListener(img5_detial, ConstantsImages.BAKE_STUDIO[4], userMessage);
        SetUpOnClickListener.setImgDetialListener(img6_detial, ConstantsImages.BAKE_STUDIO[5], userMessage);
        SetUpOnClickListener.setImgDetialListener(img7_detial, ConstantsImages.BAKE_STUDIO[6], userMessage);
    }

    private void setupViewMore(){
        SetUpOnClickListener.setViewMore(view_more_works1, "studio",userMessage);
        SetUpOnClickListener.setViewMore(view_more_works2, "studio",userMessage);
    }

    private void setTypeOnClick(){
        SetUpOnClickListener.setTypeListener(naive,"naive",getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(advanced,"advanced",getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(master,"master",getActivity(),userMessage);
    }

    private void loadImg() {
        SetUpOnClickListener.loadImgArray(images,ConstantsImages.BAKE_STUDIO);
    }

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }

    private void setupCustomView(View view) {

        LinearLayout layout3=(LinearLayout) view.findViewById(R.id.shape_container3);
        LinearLayout layout4=(LinearLayout) view.findViewById(R.id.shape_container4);
        final CriclesView customview3=new CriclesView(view.getContext(),2300);
        final CriclesView customview4=new CriclesView(view.getContext(),2300);
        customview3.setMinimumHeight(2300);
        customview3.setMinimumWidth(200);
        customview4.setMinimumHeight(2300);
        customview4.setMinimumWidth(200);
        //通知view组件重绘
        customview3.invalidate();
        layout3.addView(customview3);
        customview4.invalidate();
        layout4.addView(customview4);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        if(hasFocus){
//            studio_level_select_local = studio_level_select_container_fix.getBottom();//获取searchLayout的顶部位置
//        }
//    }
//
//    @Override
//    public void onScroll(int scrollY) {
//        if(scrollY >= studio_level_select_local){
//            if (studio_level_select.getParent()!=studio_level_select_container_float) {
//                studio_level_select_container_fix.removeView(studio_level_select);
//                studio_level_select_container_float.addView(studio_level_select);
//            }
//        }else{
//            if (studio_level_select.getParent()!=studio_level_select_container_fix) {
//                studio_level_select_container_float.removeView(studio_level_select);
//                studio_level_select_container_fix.addView(studio_level_select);
//            }
//        }
//    }

}
