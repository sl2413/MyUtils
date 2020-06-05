package com.shenl.androidTest;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.shenl.utils.MyUtils.ThreadUtils;

import java.util.HashMap;

@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment {

    private String videoUrl;
    private VideoView vv;
    private ImageView iv_zoom;
    private boolean isFor = true;

    @SuppressLint("ValidFragment")
    public VideoFragment(String VideoUrl) {
        this.videoUrl = VideoUrl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.fragment_video, null);
        iv_zoom = view.findViewById(R.id.iv_zoom);
        vv = view.findViewById(R.id.vv);
        Uri uri = Uri.parse(videoUrl);
        vv.setVideoURI(uri);

        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoUrl,new HashMap<String, String>());
        //获取第一帧
        Bitmap bitmap  = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        iv_zoom.setImageBitmap(bitmap);
        iv_zoom.setVisibility(View.VISIBLE);

        //监听播放完毕
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp){
                mp.start();
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isVisibleToUser) {
                    //相当于Fragment的onResume
                    vv.start();
                    ThreadUtils.runSonThread(new Runnable() {
                        @Override
                        public void run() {
                            while (isFor){
                                if (vv.getCurrentPosition() != 0){
                                    isFor = false;
                                    ThreadUtils.runMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            iv_zoom.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    //相当于Fragment的onPause
                    if (vv != null){
                        vv.seekTo(0);
                        vv.pause();
                    }
                    isFor = true;
                    iv_zoom.setVisibility(View.VISIBLE);
                }
            }
        },500);
    }
}
