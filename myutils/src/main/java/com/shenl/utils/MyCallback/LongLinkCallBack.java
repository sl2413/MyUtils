package com.shenl.utils.MyCallback;

import org.java_websocket.handshake.ServerHandshake;

public abstract class LongLinkCallBack {
    //长连接开启
    public void onOpen(ServerHandshake handshakedata){}
    // 收到消息回调
    public abstract void success(String message);
    //长连接关闭
    public void onClose(int code, String reason, boolean remote){}
    //长连接异常
    public void onError(Exception ex){}
}
