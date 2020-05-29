package com.shenl.utils.MyCallback;

import org.java_websocket.handshake.ServerHandshake;

public abstract class LongLinkCallBack {

    public void onOpen(ServerHandshake handshakedata){}
    // 收到消息回调
    public abstract void success(String message);
    public void onClose(int code, String reason, boolean remote){}
    public void onError(Exception ex){}
}
