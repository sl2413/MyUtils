package com.shenl.utils.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * TODO 功能：加载网络图片工具类
 *　此工具类需配合　glide-3.6.0.jar　使用
 * 参数说明:
 * 作    者:   沈 亮
 * 创建时间:   2018/12/14
 */
public class ImageUtils {
    //Vibrant 鲜艳的
    public static final int VIBRANT = 11;
    //DarkVibrant 鲜艳的暗色
    public static final int DARKVIBRANT = 12;
    //LightVibrant 鲜艳的亮色
    public static final int LIGHTVIBRANT = 13;
    //Muted  柔和的
    public static final int MUTED = 21;
    //DarkMuted 柔和的暗色
    public static final int DARKMUTED = 22;
    //LightMuted 柔和的亮色
    public static final int LIGHTMUTED = 23;

    /**
     * TODO 功能：为imageView设置bitmap图片，图片来源可以是网络或本地
     *
     * 参数说明:
     * context => 上下文
     * imgUrl => 图片地址
     * iv => 要存放图片的imageView
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static void setIvBitmap(Context context, String imgUrl, ImageView iv){
        Glide.with(context).load(imgUrl).into(iv);
    }

    /**
     * TODO : 获取imageView当中的图片
     * 参数说明 : iv => 存放图片的imageView
     * 作者 : shenl
     * 创建日期 : 2019/6/19
     * @return :
     */
    public static Bitmap getIvBitmap(ImageView iv){
        return ((BitmapDrawable)iv.getDrawable()).getBitmap();
    }

    /**
     * TODO : 获取图片中的颜色
     * 参数说明 :
     * 作者 : shenl
     * 创建日期 : 2019/6/19
     * @return :
     */
    public static int getBitmapColor(Bitmap bm,int type){
        int color = Color.BLACK;
        Palette palette = Palette.generate(bm);
        switch (type){
            case VIBRANT:
                return palette.getVibrantColor(color);
            case DARKVIBRANT:
                return palette.getDarkVibrantColor(color);
            case LIGHTVIBRANT:
                return palette.getLightVibrantColor(color);
            case MUTED:
                return palette.getMutedColor(color);
            case DARKMUTED:
                return palette.getDarkMutedColor(color);
            case LIGHTMUTED:
                return palette.getLightMutedColor(color);
        }
        return type;
    }
    /**
     * TODO 功能：资源文件转换成bitmap
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * TODO 功能：通过网络图片url地址转换成bitmap
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/6/25
     */
    public static void UrlToBitmap(final String url, final ImageLoader imageLoader){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    //这是一个一步请求，不能直接返回获取，要不然永远为null
                    //在这里得到BitMap之后记得使用Hanlder或者EventBus传回主线程，不过现在加载图片都是用框架了，很少有转化为Bitmap的需求
                    is.close();
                    imageLoader.success(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * TODO 功能：网络图片加载完成回调接口
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/6/25
     */
    interface ImageLoader{
        void success(Bitmap bitmap);
    }

}
