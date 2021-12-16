package com.jnu.myapplication;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TabLayout mMytab;
    private ViewPager mMyViewPager;
    List<Fragment> fragments;
    private List<String> titles;
    Fragment fragmentBook;
    Fragment fragmentNews;
    Fragment fragmentSale;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION},100);
    }

    private void initView() {
        mMytab = (TabLayout) findViewById(R.id.mytab);
        mMyViewPager = (ViewPager) findViewById(R.id.myViewPager);

        titles = new ArrayList<>();
        titles.add("图书");
        titles.add("新闻");
        titles.add("卖家");
        fragments = new ArrayList<>();
        fragmentBook = new BookFragment();
        fragmentNews = new WebViewFragment();
        fragmentSale = new MapViewFragment();
        fragments.add(fragmentBook);
        fragments.add(fragmentNews);
        fragments.add(fragmentSale);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);

        mMyViewPager.setAdapter(myFragmentPagerAdapter);

        mMytab.setupWithViewPager(mMyViewPager);


    }
}