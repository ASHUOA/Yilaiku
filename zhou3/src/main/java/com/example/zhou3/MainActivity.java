package com.example.zhou3;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private RadioGroup radioGroup;
    private List<String> imageadd;
    private int index=0;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    viewpager.setCurrentItem(index);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.vp);
        radioGroup = (RadioGroup) findViewById(R.id.rd);

        imageadd = new ArrayList<String>();
        imageadd.add("http://img.juhe.cn/cookbook/t/0/45_854851.jpg");
        imageadd.add("http://img.juhe.cn/cookbook/s/1/92_1630281ff0350105.jpg");
        imageadd.add("http://img.juhe.cn/cookbook/s/1/92_372112ff28389c98.jpg");

        viewpager.setAdapter(new viewadapter());
        banner();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<imageadd.size();i++)
                {
                    RadioButton rad = (RadioButton) radioGroup.getChildAt(i);
                    if (i==position%imageadd.size()){
                        rad.setChecked(true);
                    }else {
                        rad.setChecked(false);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    class viewadapter extends PagerAdapter{
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher_round)
                .build();
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView ime=new ImageView(MainActivity.this);
            ImageLoader.getInstance().displayImage(imageadd.get(position%imageadd.size()),ime,options);
           container.addView(ime);
            return ime;
        }
    }
    private void banner(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ++index;
                handler.sendEmptyMessage(1);
            }
        },0,3000);
    }
}
