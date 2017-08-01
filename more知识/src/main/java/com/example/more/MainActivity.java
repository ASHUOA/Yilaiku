package com.example.more;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.more.bean.MenuInfo;
import com.example.more.utils.Dao;
import com.example.more.utils.SeatInfo;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banner banner;
    private XListView xlistview;
    private String imageadd[] = {"http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad613.jpg"
            , "http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad617.jpg"
            , "http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad709.jpg"
    };
    private List<String> addimage;
    private xadapter adapter;
    private boolean flag = false;
    private int index = 1;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = (Banner) findViewById(R.id.banner);
        xlistview = (XListView) findViewById(R.id.xlv);
        dao = new Dao(this);
        addimage = new ArrayList<String>();
        for (String arr : imageadd
                ) {
            addimage.add(arr);
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(addimage);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setDelayTime(3000);
        banner.start();

        try {
            read("http://apis.juhe.cn/cook/query.php?key=35f9f65629365ddde8321dce7aaa198f&pn=1&rn=10&menu="+URLEncoder.encode("秘制红烧肉","utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void read(String path) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    System.out.println("=====================" + s);
                    Gson gson = new Gson();
                    MenuInfo menuInfo = gson.fromJson(s, MenuInfo.class);
                    List<MenuInfo.ResultBean.DataBean> list = menuInfo.getResult().getData();
                    for (MenuInfo.ResultBean.DataBean b:list
                         ) {
                        String title = b.getTitle();
                        boolean add = dao.add(title);
                        if (add){
                            Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,"是啊比",Toast.LENGTH_SHORT).show();

                        }
                    }
                    if (adapter == null) {
                        adapter = new xadapter(list);
                        xlistview.setAdapter(adapter);
                    } else {
                        adapter.morelaoder(list, flag);
                        adapter.notifyDataSetChanged();
                    }
                    xlistview.setPullLoadEnable(true);
                    xlistview.setXListViewListener(new XListView.IXListViewListener() {
                        @Override
                        public void onRefresh() {
                            ++index;
                            try {
                                read("http://apis.juhe.cn/cook/query.php?key=35f9f65629365ddde8321dce7aaa198f&pn=1&rn=10&menu="+URLEncoder.encode("秘制红烧肉","utf-8"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            flag = true;
                            xlistview.stopRefresh(true);

                        }

                        @Override
                        public void onLoadMore() {
                            ++index;
                            try {
                                read("http://apis.juhe.cn/cook/query.php?key=35f9f65629365ddde8321dce7aaa198f&pn=1&rn=10&menu="+URLEncoder.encode("秘制红烧肉","utf-8"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            flag = false;
                            xlistview.stopLoadMore();
                        }
                    });
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String path = params[0];
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream is = connection.getInputStream();
                        return SeatInfo.add(is);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(path);

    }

    class xadapter extends BaseAdapter {
        List<MenuInfo.ResultBean.DataBean> list;

        public xadapter(List<MenuInfo.ResultBean.DataBean> list) {
            this.list = list;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher_round)
                .build();

        public void morelaoder(List<MenuInfo.ResultBean.DataBean> lists, boolean flag) {
            for (MenuInfo.ResultBean.DataBean bean : lists
                    ) {
                if (flag) {
                    list.add(0, bean);
                } else {
                    list.add(bean);
                }
            }
        }

        @Override
        public int getCount() {
            return list != null ? list.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            viewholder vh1 = null;
            viewholder2 vh2 = null;
            switch (type) {
                case 0:
                    if (convertView == null) {
                        vh1 = new viewholder();
                        convertView = convertView.inflate(MainActivity.this, R.layout.item, null);
                        vh1.im1 = (ImageView) convertView.findViewById(R.id.im1);
                        vh1.tv1 = (TextView) convertView.findViewById(R.id.tv1);
                        convertView.setTag(vh1);
                    } else {
                        vh1 = (viewholder) convertView.getTag();
                    }
                    vh1.tv1.setText(list.get(position).getTags());
                    com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(list.get(position).getAlbums().get(0), vh1.im1, options);
                    break;
                case 1:
                    if (convertView == null) {
                        vh2 = new viewholder2();
                        convertView = convertView.inflate(MainActivity.this, R.layout.item2, null);
                        vh2.im2 = (ImageView) convertView.findViewById(R.id.im2);
                        vh2.tv2 = (TextView) convertView.findViewById(R.id.tv2);
                        convertView.setTag(vh2);
                    } else {
                        vh2 = (viewholder2) convertView.getTag();
                    }
                    vh2.tv2.setText(list.get(position).getTags());
                    com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(list.get(position).getAlbums().get(0), vh2.im2, options);
                    break;
            }
            return convertView;
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    class viewholder {
        ImageView im1;
        TextView tv1;
    }

    class viewholder2 {
        ImageView im2;
        TextView tv2;
    }
}
