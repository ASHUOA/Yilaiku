package com.example.duocengjiazai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.lv);
        listview.setAdapter(new myadapter());
    }
    class myadapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (position%2==0)
            {
                return 0;
            }else
            {
                return 1;
            }

        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           /* TextView textView=new TextView(MainActivity.this);
            textView.setText("fsfasfcac"+position);
            textView.setHeight(100);*/
            int type = getItemViewType(position);
            ViewHolder holder = null;
            ViewHolder2 holder2 = null;
            switch (type){
                case 1:
                    if (convertView==null){
                        holder = new ViewHolder();
                        convertView= convertView.inflate(MainActivity.this,R.layout.item1,null);
                        holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
                        holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                        convertView.setTag(holder);
                    }else{
                        holder = (ViewHolder) convertView.getTag();
                    }

                    break;
                case 0:
                    if (convertView==null){
                        holder2 = new ViewHolder2();
                        convertView= convertView.inflate(MainActivity.this,R.layout.item2,null);
                        holder2.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
                        holder2.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                        convertView.setTag(holder2);
                    }else{
                        holder2 = (ViewHolder2) convertView.getTag();
                    }
                    break;
            }
            switch (type){
                case 0:
                    holder2.tvContent.setText("大家都好都好");
                    break;
                case 1:
                    holder.tvContent.setText("附近的开发基地附近的咖啡机");
                    break;
            }
            return convertView;
        }

        class ViewHolder{

            ImageView ivIcon;
            TextView tvContent;

        }

        class ViewHolder2{
            ImageView ivIcon;
            TextView tvContent;


        }

    }

}
