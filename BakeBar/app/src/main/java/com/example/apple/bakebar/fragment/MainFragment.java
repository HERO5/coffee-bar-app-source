package com.example.apple.bakebar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.apple.bakebar.Entity.ConstantsImages;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.SetUpOnClickListener;
import com.example.apple.bakebar.activity.CallSystemCamera;
import com.example.apple.bakebar.modle.UserMessage;
import com.example.apple.bakebar.view.CloseCakeWareRectWithHorizontalBottom;
import com.example.apple.bakebar.view.CloseRadomWareRectWithHorizontalTop;
import com.example.apple.bakebar.view.Kanner;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/10/25.
 */

public class MainFragment extends BaseFragment  implements AppBarLayout.OnOffsetChangedListener {

    private final String TAG = "BlankFragment";

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private FloatingSearchView mSearchView;
    private AppBarLayout mAppBar;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";

    private Kanner kanner;

    private List<ImageView> images;

    private ImageView typeCoffee;
    private ImageView typeCake;

    private FloatingActionButton img1_collect;
    private FloatingActionButton img1_detial;
    private FloatingActionButton img2_collect;
    private FloatingActionButton img2_detial;
    private FloatingActionButton img3_collect;
    private FloatingActionButton img3_detial;
    private FloatingActionButton img4_collect;
    private FloatingActionButton img4_detial;
    private FloatingActionButton img5_collect;
    private FloatingActionButton img5_detial;
    private FloatingActionButton img6_collect;
    private FloatingActionButton img6_detial;

    private TextView view_more_works1;
    private TextView view_more_works2;

    private UserMessage userMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bake_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        mAppBar = (AppBarLayout) view.findViewById(R.id.appbar);

        mAppBar.addOnOffsetChangedListener(this);

        kanner = (Kanner)view.findViewById(R.id.kanner);

        images = new ArrayList<>();

        images.add((ImageView)view.findViewById(R.id.img1));
        images.add((ImageView)view.findViewById(R.id.img2));
        images.add((ImageView)view.findViewById(R.id.img3));
        images.add((ImageView)view.findViewById(R.id.img4));
        images.add((ImageView)view.findViewById(R.id.img5));
        images.add((ImageView)view.findViewById(R.id.img6));

        typeCoffee = (ImageView) view.findViewById(R.id.type_coffee);
        typeCake = (ImageView) view.findViewById(R.id.type_cake);

        img1_collect = (FloatingActionButton) view.findViewById(R.id.img1_collect);
        img1_detial = (FloatingActionButton) view.findViewById(R.id.img1_detial);
        img2_collect = (FloatingActionButton) view.findViewById(R.id.img2_collect);
        img2_detial = (FloatingActionButton) view.findViewById(R.id.img2_detial);
        img3_collect = (FloatingActionButton) view.findViewById(R.id.img3_collect);
        img3_detial = (FloatingActionButton) view.findViewById(R.id.img3_detial);
        img4_collect = (FloatingActionButton) view.findViewById(R.id.img4_collect);
        img4_detial = (FloatingActionButton) view.findViewById(R.id.img4_detial);
        img5_collect = (FloatingActionButton) view.findViewById(R.id.img5_collect);
        img5_detial = (FloatingActionButton) view.findViewById(R.id.img5_detial);
        img6_collect = (FloatingActionButton) view.findViewById(R.id.img6_collect);
        img6_detial = (FloatingActionButton) view.findViewById(R.id.img6_detial);

        view_more_works1 = (TextView)view.findViewById(R.id.view_more_best);
        view_more_works2 = (TextView)view.findViewById(R.id.view_more_shop);

        if(getActivity().getIntent().getSerializableExtra("userInfo")!=null){
            userMessage = (UserMessage) (getActivity().getIntent().getSerializableExtra("userInfo"));
        }

        setupDrawer();
        setupSearchBar(view);
        setupKanner();
        setupCustomView(view);
        loadImg();
        setupViewMore();
        setupImgOnClick();
        setupTypeOnClick();
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

                switch (item.getItemId()){
                    case R.id.search_menu:
                        String queryStr = mSearchView.getQuery().toString();
                        SetUpOnClickListener.doQuery(view,queryStr,getActivity(),userMessage);
                        break;
                    case R.id.call_camera:
                        //just print action
                        Toast.makeText(getActivity().getApplicationContext(), item.getTitle(),
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        intent.setClass(view.getContext(), CallSystemCamera.class);
                        //第一个参数就是获取当前的context。在activity类中可以用CurrentActivity.this代替
                        //这里第二个参数是将跳到的activity
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.action_change_colors:
                        mIsDarkSearchTheme = true;
                        //demonstrate setting colors for items
                        mSearchView.setBackgroundColor(Color.parseColor("#787878"));
                        mSearchView.setViewTextColor(Color.parseColor("#e9e9e9"));
                        mSearchView.setHintTextColor(Color.parseColor("#e9e9e9"));
                        mSearchView.setActionMenuOverflowColor(Color.parseColor("#e9e9e9"));
                        mSearchView.setMenuItemIconColor(Color.parseColor("#e9e9e9"));
                        mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
                        mSearchView.setClearBtnColor(Color.parseColor("#e9e9e9"));
                        mSearchView.setDividerColor(Color.parseColor("#BEBEBE"));
                        mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
                        break;
                    default:
                        break;
                }

            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            }

            @Override
            public void onSearchAction(String currentQuery) {
                Toast.makeText(getActivity(), "onSearchAction():"+currentQuery, Toast.LENGTH_LONG).show();
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

            }

            @Override
            public void onFocusCleared() {

            }
        });
    }

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }

    private void setupKanner(){
        String[] imagesRes = { ConstantsImages.BAKE_MAIN[6],ConstantsImages.BAKE_MAIN[7],ConstantsImages.BAKE_MAIN[8],ConstantsImages.BAKE_MAIN[9]};
        kanner.setImagesUrl(imagesRes);
    }

    private void loadImg() {
        SetUpOnClickListener.loadImgArray(images,ConstantsImages.BAKE_MAIN);
    }

    private void setupCustomView(View view) {

        LinearLayout layout1=(LinearLayout) view.findViewById(R.id.shape_container1);
        LinearLayout layout2=(LinearLayout) view.findViewById(R.id.shape_container2);
        final CloseRadomWareRectWithHorizontalTop customview1=new CloseRadomWareRectWithHorizontalTop(view.getContext(),null,Color.parseColor("#F08080"),250,true);
        final CloseCakeWareRectWithHorizontalBottom customview2=new CloseCakeWareRectWithHorizontalBottom(view.getContext(),null,Color.parseColor("#D2691E"),150,true);
        customview1.setMinimumHeight(500);
        customview1.setMinimumWidth(300);
        customview2.setMinimumHeight(400);
        customview2.setMinimumWidth(300);
        //通知view组件重绘
        customview1.invalidate();
        customview2.invalidate();
        layout1.addView(customview1);
        layout2.addView(customview2);
    }

    private void setupImgOnClick(){
        Activity activity = getActivity();
        SetUpOnClickListener.setImgCollectListener(img1_collect, ConstantsImages.BAKE_MAIN[0], activity);
        SetUpOnClickListener.setImgCollectListener(img2_collect, ConstantsImages.BAKE_MAIN[1], activity);
        SetUpOnClickListener.setImgCollectListener(img3_collect, ConstantsImages.BAKE_MAIN[2], activity);
        SetUpOnClickListener.setImgCollectListener(img4_collect, ConstantsImages.BAKE_MAIN[3], activity);
        SetUpOnClickListener.setImgCollectListener(img5_collect, ConstantsImages.BAKE_MAIN[4], activity);
        SetUpOnClickListener.setImgCollectListener(img6_collect, ConstantsImages.BAKE_MAIN[5], activity);

        SetUpOnClickListener.setImgDetialListener(img1_detial, ConstantsImages.BAKE_MAIN[0], userMessage);
        SetUpOnClickListener.setImgDetialListener(img2_detial, ConstantsImages.BAKE_MAIN[1], userMessage);
        SetUpOnClickListener.setImgDetialListener(img3_detial, ConstantsImages.BAKE_MAIN[2], userMessage);
        SetUpOnClickListener.setImgDetialListener(img4_detial, ConstantsImages.BAKE_MAIN[3], userMessage);
        SetUpOnClickListener.setImgDetialListener(img5_detial, ConstantsImages.BAKE_MAIN[4], userMessage);
        SetUpOnClickListener.setImgDetialListener(img6_detial, ConstantsImages.BAKE_MAIN[5], userMessage);
    }

    private void setupTypeOnClick(){
        SetUpOnClickListener.setTypeListener(typeCoffee, "coffee", getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(typeCake, "cake", getActivity(),userMessage);
    }

    private void setupViewMore(){
        SetUpOnClickListener.setViewMore(view_more_works1, "studio",userMessage);
        SetUpOnClickListener.setViewMore(view_more_works2, "studio",userMessage);
    }
    //把图片转换成bitmap形式传递通过intent形式传递过去
    private Bitmap setimage(ImageView view1){
        Bitmap image = ((BitmapDrawable)view1.getDrawable()).getBitmap();
        Bitmap bitmap1 = Bitmap.createBitmap(image);
        return bitmap1;
    }
}
