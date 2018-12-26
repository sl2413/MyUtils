package com.shenl.utils.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
