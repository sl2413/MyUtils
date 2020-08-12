package com.shenl.utils.MyUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shenl.utils.MyCallback.PermissionListener;
import com.shenl.utils.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * TODO 功能：加载网络图片工具类
 * 　此工具类需配合　glide-3.6.0.jar　使用
 * 参数说明:
 * 作    者:   沈 亮
 * 创建时间:   2018/12/14
 */
public class FileUtils {
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
     * <p>
     * 参数说明:
     * context => 上下文
     * imgUrl => 图片地址
     * iv => 要存放图片的imageView
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static void setIvBitmap(Context context, String imgUrl, ImageView iv) {
        setIvBitmap(context, imgUrl, iv, 200, false);
    }

    /**
     * TODO 功能：为imageView设置bitmap图片，图片来源可以是网络或本地
     * <p>
     * 参数说明:
     * context => 上下文
     * imgUrl => 图片地址
     * iv => 要存放图片的imageView
     * size => 图片质量默认为200*200
     * isSeat => 是否显示占位图，默认为不显示
     * 作    者:   沈 亮
     * 创建时间:   2018/12/14
     */
    public static void setIvBitmap(Context context, String imgUrl, ImageView iv, int size, boolean isSeat) {
        DrawableRequestBuilder<String> override = Glide.with(context)
                .load(imgUrl)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(size, size);
        if (isSeat) {
            override.placeholder(R.drawable.no_picture).into(iv);
        } else {
            override.into(iv);
        }
    }

    /**
     * TODO : 获取imageView当中的图片
     * 参数说明 : iv => 存放图片的imageView
     * 作者 : shenl
     * 创建日期 : 2019/6/19
     *
     * @return :
     */
    public static Bitmap getIvBitmap(ImageView iv) {
        return ((BitmapDrawable) iv.getDrawable()).getBitmap();
    }

    /**
     * TODO : 获取图片中的颜色
     * 参数说明 :
     * 作者 : shenl
     * 创建日期 : 2019/6/19
     *
     * @return :
     */
    public static int getBitmapColor(Bitmap bm, int type) {
        int color = Color.BLACK;
        Palette palette = Palette.generate(bm);
        switch (type) {
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
     * <p>
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
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/6/25
     */
    public static void UrlToBitmap(final String url, final ImageLoader imageLoader) {
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
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    //这是一个一步请求，不能直接返回获取，要不然永远为null
                    //在这里得到BitMap之后记得使用Hanlder或者EventBus传回主线程，不过现在加载图片都是用框架了，很少有转化为Bitmap的需求
                    is.close();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            imageLoader.success(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * TODO 功能：图片保存到手机
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/7/31
     */
    private void ImageToLocal(Context context,ImageView img) {
        img.buildDrawingCache();
        Bitmap bitmap=img.getDrawingCache();
        //将Bitmap 转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG , 100 , stream);
        byte[] byteArray = stream.toByteArray();
        File dir=new File(Environment.getExternalStorageDirectory ().getAbsolutePath()+"/picture" );
        if(!dir.isFile()){
            dir.mkdir();
        }
        String fileName=System.currentTimeMillis() +".png";
        File file=new File(dir,fileName);
        try {
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(byteArray, 0 , byteArray.length);
            fos.flush();
            fos.close();
            //用广播通知相册进行更新相册
            // 把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
            PageUtils.showToast(context,"保存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            PageUtils.showToast(context,"保存失败");
        } catch (IOException e) {
            e.printStackTrace();
            PageUtils.showToast(context,"保存失败");
        }
    }

    /**
     * TODO 功能：bigmap转file文件
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/7/17
     */
    public static File compressImage(Bitmap bitmap, long KB) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > KB) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
            PageUtils.showLog("压缩图：" + length);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        //图片名
        String filename = format.format(date);

        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        PageUtils.showLog("转换完成文件大小：" + file.length());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * TODO 功能：文件路径转成文件
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/21
     */
    public static File compress(Activity activity, String path, long KB) {
        PageUtils.showLog("图片路径：" + path);
        File file = new File(path);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //只请求图片宽高，不解析图片像素
        opts.inJustDecodeBounds = true;
        //返回null,获取图片宽高，保存在opts对象中
        //BitmapFactory.decodeFile(path, opts);

        //获取图片宽高
        int imageWidth = opts.outWidth;
        int imageHeight = opts.outHeight;

        //获取屏幕宽高
        WindowManager manager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        int screenHeight = dm.heightPixels;// 获取屏幕分辨率高度

        //计算缩放比例
        int scale = 1;
        int Width = imageWidth / screenWidth;
        int Height = imageHeight / screenHeight;

        //判断取那个比例
        if (Width >= Height) {
            scale = Width;
        } else if (Width < Height && Height > 1) {
            scale = Height;
        }

        //设置缩小比例
        opts.inSampleSize = scale;
        opts.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(path, opts);
        File compressfile = compressImage(bm, KB);
        return compressfile;
    }

    /**
     * TODO 功能：打开手机相册页面
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/21
     */
    public static void openPhoto(final Activity activity) {
        //动态需要请求的权限 ,例如下代码
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        getPermissions(activity, PERMISSIONS_STORAGE, new PermissionListener(activity) {
            @Override
            public void onGranted() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                // intent.setType("image/*;video/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                activity.startActivityForResult(intent, 1000);
            }
        });
    }

    /**
     * TODO 功能：打开本地文件管理器
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/29
     */
    public static void OpenFolder(final Activity activity) {
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        getPermissions(activity, PERMISSIONS_STORAGE, new PermissionListener(activity) {
            @Override
            public void onGranted() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                // intent.setType("image/*;video/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                activity.startActivityForResult(intent, 1000);
            }
        });
    }

    /**
     * TODO 功能：获取选择的文件
     * <p>
     * 参数说明: 该方法要放到onActivityResult里面
     * 作    者:   沈  亮
     * 创建时间:   2019/11/21
     */
    public static File getFile(Context context, Intent data) {
        Uri uri = data.getData();
        if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
            String path = uri.getPath();
            File file = new File(path);
            String filename = file.getName();
            return file;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以后
            String path = UriFormateUtils.getInstance().getPath(context.getApplicationContext(), uri);
            File file = new File(path);
            String filename = file.getName();
            return file;

        } else {//4.4以下下系统调用方法
            String path = UriFormateUtils.getInstance().getRealPathFromURI(uri, context.getApplicationContext());
            File file = new File(path);
            String filename = file.getName();
            return file;
        }
    }

    /**
     * TODO 功能：网络图片加载完成回调接口
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/6/25
     */
    public interface ImageLoader {
        void success(Bitmap bitmap);
    }

    /**
     * TODO 功能：请求所需权限
     * <p>
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2019/11/21
     */
    public static void getPermissions(Activity activity, String[] PERMISSIONS_STORAGE, PermissionListener listener) {
        ArrayList<String> list = new ArrayList<>();
        //判断android版本
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            //遍历权限, 找出未开启的权限
            for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
                if (ActivityCompat.checkSelfPermission(activity, PERMISSIONS_STORAGE[i]) != PackageManager.PERMISSION_GRANTED) {
                    list.add(PERMISSIONS_STORAGE[i]);
                }
            }
            if (!list.isEmpty()) {
                //添加请求权限
                String[] PERMISSIONS = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    PERMISSIONS[i] = list.get(i);
                }
                //发送权限请求
                ActivityCompat.requestPermissions(activity, PERMISSIONS, 1000);
            } else {
                listener.onGranted();
            }
        }
    }

}
