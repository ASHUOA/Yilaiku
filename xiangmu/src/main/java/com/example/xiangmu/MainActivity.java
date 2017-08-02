package com.example.xiangmu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.test_iv)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        testImage();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Event(R.id.test_click_btn)
    private void testClick(View view){
        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
    }

    @Event(R.id.test_post_btn)
    private void testpost(View view){
        String path="http://route.showapi.com/138-46";
        RequestParams params=new RequestParams(path);
        params.addQueryStringParameter("showapi_appid","38170");
        params.addQueryStringParameter("showapi_sign","");
        params.addQueryStringParameter("prov","");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this,"post",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    @Event(type = View.OnLongClickListener.class,value = R.id.test_get_btn)
    private boolean testget(View view){
        String path="http://route.showapi.com/138-46";
        RequestParams params=new RequestParams(path);
        params.addQueryStringParameter("showapi_appid","38170");
        params.addQueryStringParameter("showapi_sign","");
        params.addQueryStringParameter("prov","");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this,"get",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return true;
    }
    public void testImage(){
        ImageOptions options= new ImageOptions.Builder()
                .setFadeIn(true)
                .setCrop(true)
                .setSize(200,200)
                .setUseMemCache(true)
                .build();
        x.image().bind(imageView,"http://ytjr.gnway.cc:88//upload/YS01/img/4144148607704755a547da45dd15a473.png",options);

    }

}
