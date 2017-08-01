package com.example.more;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by fanyishuo on 2017/7/25.
 */

public class ImageAPP extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        String path= Environment.getExternalStorageDirectory().getPath()+"/kkk";
        File file=new File(path);
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .threadPriority(1000)
                .threadPoolSize(3)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheFileCount(50)
                .diskCacheSize(50*1024*1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheSize(2*1024*1024)
                .memoryCacheExtraOptions(480,800)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
