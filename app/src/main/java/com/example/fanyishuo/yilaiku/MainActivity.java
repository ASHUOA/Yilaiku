package com.example.fanyishuo.yilaiku;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.fanyishuo.yilaiku.bean.MenuInfo;
import com.example.fanyishuo.yilaiku.utils.StreamTools;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private XListView xlistview;
    private int index=1;
    private myadapter adapter;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xlistview = (XListView) findViewById(R.id.xlv);

        read("http://apis.juhe.cn/cook/query.php", index+ "", 10 + "");


        xlistview.setPullLoadEnable(true);
        xlistview.setXListViewListener(this);


    }
    public void read(String path,String pn,String rn){
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s!=null)
                {
                    Gson gson=new Gson();
                    MenuInfo menuInfo = gson.fromJson(s, MenuInfo.class);
                    List<MenuInfo.ResultBean.DataBean> list = menuInfo.getResult().getData();

                    if (adapter==null) {
                        adapter = new myadapter(list);
                        xlistview.setAdapter(adapter);
                    }else {
                        adapter.loadmore(list,flag);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String path=params[0];
                    String pn=params[1];
                    String rn=params[2];
                    URL url=new URL(path);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    OutputStream os = connection.getOutputStream();
                    os.write(("key=35f9f65629365ddde8321dce7aaa198f&menu=" +
                            URLEncoder.encode("秘制红烧肉" , "utf-8") + "&pn=" + pn + "&rn=" + rn).getBytes());
                    os.flush();
                    int code = connection.getResponseCode();
                    if (code==200){
                        InputStream is = connection.getInputStream();
                        String json = StreamTools.readFromNetWork(is);
                        return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(path,pn,rn);
    }

    @Override
    public void onRefresh() {
        ++index;
        read("http://apis.juhe.cn/cook/query.php", index+ "", 10 + "");
        flag=true;
        xlistview.stopRefresh(true);

    }

    @Override
    public void onLoadMore() {
        ++index;
        read("http://apis.juhe.cn/cook/query.php", index+ "", 10 + "");
        flag=false;
        xlistview.stopLoadMore();

    }

    class myadapter extends BaseAdapter{

        List<MenuInfo.ResultBean.DataBean> list;

        public myadapter(List<MenuInfo.ResultBean.DataBean> list) {
            this.list = list;
        }
        public void loadmore(List<MenuInfo.ResultBean.DataBean> lists,boolean flag){
            for (MenuInfo.ResultBean.DataBean bean:lists) {
                if (flag)
                list.add(0,bean);
                else
                    list.add(bean);

            }

        }

        @Override
        public int getCount() {
            return list!=null?list.size():0;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView=new TextView(MainActivity.this);
            textView.setHeight(100);
            textView.setText(list.get(position).getTitle());

            return textView;
        }
    }
}
