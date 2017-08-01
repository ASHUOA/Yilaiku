package com.example.zhou3;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by fanyishuo on 2017/7/22.
 */

public class ImageAPP extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        String path= Environment.getExternalStorageDirectory().getPath()+"/creay";
        File file=new File(path);
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .threadPriority(100)
                .threadPoolSize(3)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheSize(50*1024*1024)
                .diskCacheFileCount(50)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheExtraOptions(480,800)
                .memoryCacheSize(2*1024*1024)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
