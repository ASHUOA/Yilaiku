package com.example.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private ViewPager viewpager;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        final List<Fragment> list=new ArrayList<Fragment>();
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
            @Override
            public int getCount() {
                return list.size();
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            aaa(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        bt1.setTextColor(Color.RED);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                count=0;
                break;
            case R.id.bt2:
                count=1;
                break;
            case R.id.bt3:
                count=2;
                break;
            case R.id.bt4:
                count=3;
                break;
        }
        viewpager.setCurrentItem(count);
        aaa(count);
    }
    public void aaa(int count){
        bt1.setTextColor(count==0? Color.RED:Color.BLACK);
        bt2.setTextColor(count==1? Color.RED:Color.BLACK);
        bt3.setTextColor(count==2? Color.RED:Color.BLACK);
        bt4.setTextColor(count==3? Color.RED:Color.BLACK);
    }
}
