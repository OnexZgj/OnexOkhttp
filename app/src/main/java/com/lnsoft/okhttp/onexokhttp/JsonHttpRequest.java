package com.lnsoft.okhttp.onexokhttp;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * des：
 * author：onexzgj
 * time：2019/1/23
 */
public class JsonHttpRequest implements HttpRequest {


    private String mUrl;
    private byte[] mParams;
    private CallBackListener mCallBackListener;
    private HttpURLConnection mUrlConnection;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public void setParams(byte[] params) {
        this.mParams = params;
    }

    @Override
    public void setListener(CallBackListener callBackListener) {
        this.mCallBackListener = callBackListener;
    }

    @Override
    public void execute() {
        URL url = null;
        try {
            url = new URL(this.mUrl);
            mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setConnectTimeout(8000);
//            mUrlConnection.setUseCaches(false);
            mUrlConnection.setReadTimeout(8000);
//            mUrlConnection.setDoInput(true);
//            mUrlConnection.setDoOutput(true);
            mUrlConnection.setRequestMethod("GET");// 提交模式
//            mUrlConnection.setInstanceFollowRedirects(true);
//            mUrlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//            mUrlConnection.connect();
//            OutputStream out = mUrlConnection.getOutputStream();
//            BufferedOutputStream bos = new BufferedOutputStream(out);
//            if (mParams != null)
//                bos.write(mParams);
//
//            bos.flush();
//            out.close();
//            bos.close();

            if (mUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = mUrlConnection.getInputStream();
                mCallBackListener.onSuccess(in);
            } else {
                throw new RuntimeException("返回结果不是200");
            }
        } catch (Exception e) {
            mCallBackListener.onFailure(e);
            throw new RuntimeException("请求出错了");
        } finally {
            mUrlConnection.disconnect();
        }
    }
}
