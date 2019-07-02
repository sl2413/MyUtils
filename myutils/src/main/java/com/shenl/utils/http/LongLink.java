package com.shenl.utils.http;

import com.shenl.utils.MyCallback.LongLinkCallBack;
import com.shenl.utils.MyUtils.PageUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * TODO 功能：长链接工具类
 *
 * 参数说明:
 * 所需依赖==> org.java-websocket:Java-WebSocket:1.4.0
 * 作    者:   沈 亮
 * 创建时间:   2019/7/2
 */
public class LongLink extends WebSocketClient {

    private LongLinkCallBack callBack;

    public LongLink(String url, LongLinkCallBack callBack) {
        super(URI.create(url), new Draft_6455());
        this.callBack = callBack;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        PageUtils.showLog("长链接开启......");
    }

    @Override
    public void onMessage(String message){
        callBack.success(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        PageUtils.showLog("长链接关闭......");
    }

    @Override
    public void onError(Exception ex) {
        PageUtils.showLog("长链接错误..."+ex.getMessage());
    }
}
