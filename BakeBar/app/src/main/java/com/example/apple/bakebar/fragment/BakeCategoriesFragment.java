package com.example.apple.bakebar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.SetUpOnClickListener;
import com.example.apple.bakebar.activity.CallSystemCamera;
import com.example.apple.bakebar.activity.ImageSelectActivity;
import com.example.apple.bakebar.modle.UserMessage;

/**
 * Created by apple on 2017/10/26.
 */

public class BakeCategoriesFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener {

    private final String TAG = "BlankFragment";

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private FloatingSearchView mSearchView;
    private AppBarLayout mAppBar;
    private ImageView chain;
    private ImageView france;
    private ImageView italy;
    private ImageView japan;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";

    private UserMessage userMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bake_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        mAppBar = (AppBarLayout) view.findViewById(R.id.appbar);

        mAppBar.addOnOffsetChangedListener(this);

        chain = (ImageView) view.findViewById(R.id.chain);
        france = (ImageView) view.findViewById(R.id.france);
        italy = (ImageView) view.findViewById(R.id.italy);
        japan = (ImageView) view.findViewById(R.id.japan);

        if(getActivity().getIntent().getSerializableExtra("userInfo")!=null){
            userMessage = (UserMessage) (getActivity().getIntent().getSerializableExtra("userInfo"));
        }
        setupDrawer();
        setupSearchBar(view);
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

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }

    private void setTypeOnClick(){
        SetUpOnClickListener.setTypeListener(chain,"chain",getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(france,"france",getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(italy,"italy",getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(japan,"japan",getActivity(),userMessage);
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
