package com.example.apple.bakebar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.apple.bakebar.Entity.ConstantsHttp;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.SetUpOnClickListener;
import com.example.apple.bakebar.activity.AccountManageActivity;
import com.example.apple.bakebar.activity.CallSystemCamera;
import com.example.apple.bakebar.adapter.WGalleryAdapter;
import com.example.apple.bakebar.modle.UserMessage;
import com.example.apple.bakebar.view.CircleImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.wgallery.android.WGallery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/10/26.
 */

public class PersonalPageFragment extends BaseFragment  implements AppBarLayout.OnOffsetChangedListener {

    private UserMessage userMessage;
    private FloatingSearchView mSearchView;
    private AppBarLayout mAppBar;
    private WGallery gallery;
    private WGalleryAdapter wGalleryAdapter;
    private int repertoryId;
    private View view;
    private LinearLayout works;
    private LinearLayout orders;
    private LinearLayout settings;
    private LinearLayout collections;
    private LinearLayout cart;
    private LinearLayout acount_manage;
    private LinearLayout follow;
    private LinearLayout fund;
    private LinearLayout message;

    private CircleImageView user_avator;

    private ImageView collection;

    public PersonalPageFragment(int repertoryId){
        super();
        this.repertoryId = repertoryId;
    }

    public void setRepertoryId(int repertoryId){
        this.repertoryId = repertoryId;
        gallery.setAdapter(initGalleryAdapter(view));
    }

    public int getRepertoryId() {
        return repertoryId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bake_personal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity().getIntent().getSerializableExtra("userInfo")!=null){
            userMessage = (UserMessage) (getActivity().getIntent().getSerializableExtra("userInfo"));
        }

        this.view = view;
        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        mAppBar = (AppBarLayout) view.findViewById(R.id.appbar);
        mAppBar.addOnOffsetChangedListener(this);

        user_avator = (CircleImageView) view.findViewById(R.id.user_avator);

        works = (LinearLayout) view.findViewById(R.id.works);
        orders = (LinearLayout) view.findViewById(R.id.orders);
        settings = (LinearLayout) view.findViewById(R.id.settings);
        collections = (LinearLayout) view.findViewById(R.id.collections);
        cart = (LinearLayout) view.findViewById(R.id.cart);
        acount_manage = (LinearLayout) view.findViewById(R.id.personal_account);
        follow = (LinearLayout) view.findViewById(R.id.follow);
        fund = (LinearLayout) view.findViewById(R.id.fund);
        message = (LinearLayout) view.findViewById(R.id.message);

        acount_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMessage.getState()==UserMessage.LOGIN_ERROR){
                    Toast.makeText(view.getContext(), "You Have Not Login", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), AccountManageActivity.class);
                    intent.putExtra("userInfo", userMessage);
                    view.getContext().startActivity(intent);
                }
            }
        });

        setTempImgOnClick(works,"Works");
        setTempImgOnClick(orders,"Orders");
        setTempImgOnClick(settings,"Settings");
        setTempImgOnClick(collections,"Collections");
        setTempImgOnClick(cart,"Cart");
        setTempImgOnClick(follow,"Follow");
        setTempImgOnClick(fund,"Fund");
        setTempImgOnClick(message,"Message");

        gallery = (WGallery)view.findViewById(R.id.wgallery);
        gallery.setAdapter(initGalleryAdapter(view));

        collection = (ImageView) view.findViewById(R.id.collection);

        initImageLoader(view.getContext());
        showUserInfo();
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
                        //just print action
                        Toast.makeText(getActivity().getApplicationContext(), item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        intent.setClass(view.getContext(), CallSystemCamera.class);
                        //第一个参数就是获取当前的context。在activity类中可以用CurrentActivity.this代替
                        //这里第二个参数是将跳到的activity
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.personal_settings:
                        if(userMessage.getState()==UserMessage.LOGIN_ERROR){
                            Toast.makeText(view.getContext(), "You Have Not Login", Toast.LENGTH_LONG).show();
                        }else {
                            intent.setClass(view.getContext(), AccountManageActivity.class);
                            intent.putExtra("userInfo", userMessage);
                            view.getContext().startActivity(intent);
                        }
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

    protected BaseAdapter initGalleryAdapter(View view) {
//        throw new RuntimeException("必须重写该方法");

        return new WGalleryAdapter(view.getContext(),repertoryId);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    private void showUserInfo() {
        List<ImageView> imgs = new ArrayList<ImageView>();
        imgs.add(user_avator);

        if(userMessage.getState()==UserMessage.LOGIN_ERROR){
            SetUpOnClickListener.loadImgArray(imgs, new String[]{" "});
        }else{
            SetUpOnClickListener.loadImgArray(imgs, new String[]{ConstantsHttp.BASEURL+ConstantsHttp.BASEURL_IMG+userMessage.getUser().getUserImg()});
        }
    }

    private void setTempImgOnClick(View view, final String toastStr){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), toastStr, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupModelOnClick(){
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}