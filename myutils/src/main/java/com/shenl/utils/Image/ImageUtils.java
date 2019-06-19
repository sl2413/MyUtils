package com.shenl.utils.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

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

                /*Palette palette = Palette.generate(bitmap);
                PageUtils.showLog(palette.getVibrantColor(Color.BLACK)+"");
                PageUtils.showLog(palette.getDarkVibrantColor(Color.BLACK)+"");
                PageUtils.showLog(palette.getLightVibrantColor(Color.BLACK)+"");
                PageUtils.showLog(palette.getMutedColor(Color.BLACK)+"");
                PageUtils.showLog(palette.getDarkMutedColor(Color.BLACK)+"");
                PageUtils.showLog(palette.getLightVibrantColor(Color.BLACK)+"");*/

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

}
