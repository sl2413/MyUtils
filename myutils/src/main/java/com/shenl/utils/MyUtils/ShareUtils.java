package com.shenl.utils.MyUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShareUtils {

    /**
     * TODO 功能：分享文本内容
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/7/4
     */
    public static void ShareText(Context context,String text){
        Intent textIntent = new Intent();
        textIntent.setPackage("com.tencent.mm");
        textIntent.setAction(Intent.ACTION_SEND);//设置分享行为
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(textIntent, "分享到"));
    }

    /**
     * TODO 功能：分享图片内容
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/7/4
     */
    public static void ShareImage(Context context,List<File> files){
        ArrayList<Uri> uriList = new ArrayList<>();
        for (int i=0;i<files.size();i++){
            uriList.add(Uri.fromFile(files.get(i)));
        }
        Intent shareIntent = new Intent();
        shareIntent.setPackage("com.tencent.mm");
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * TODO 功能：创建要分享的图片列表
     *
     * 参数说明:
     * 作    者:   沈  亮
     * 创建时间:   2020/7/5
     */
    public static void CreateShareFile(final Context context, final String urls, final CallBack callBack){
            Glide.with(context).load(urls).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                    try {
                        //如果手机已插入sd卡,且app具有读写sd卡的权限
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            String filePath = Environment.getExternalStorageDirectory()+"";
                            File dir1 = new File(filePath);
                            if (!dir1.exists()){
                                dir1.mkdirs();
                            }
                            String Path = filePath+ "/" + System.currentTimeMillis()+".png";
                            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                            FileOutputStream output = new FileOutputStream(Path);
                            output.write(bytes);
                            //将bytes写入到输出流中
                            output.close();
                            //关闭输出流
                            PageUtils.showLog("图片已成功保存到"+Path);
                            callBack.Result(new File(Path));
                        } else{
                            PageUtils.showLog("SD卡不存在或者不可读写");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public interface CallBack{
        void Result(File file);
    }

}
