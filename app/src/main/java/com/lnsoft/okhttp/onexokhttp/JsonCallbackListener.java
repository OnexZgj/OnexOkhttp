package com.lnsoft.okhttp.onexokhttp;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * des：
 * author：onexzgj
 * time：2019/1/23
 */
public class JsonCallbackListener<T> implements CallBackListener {


    private  ResponCallback mResponCallback;
    private Class<T> responseClass;

    private Handler handler=new Handler(Looper.getMainLooper());


    public JsonCallbackListener(Class<T> responseClass,ResponCallback responCallback) {
        this.responseClass = responseClass;
        this.mResponCallback=responCallback;
    }

    @Override
    public void onSuccess(InputStream in) {
        try {
            String content = getContent(in);
            final T clazz = JSON.parseObject(content, responseClass);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mResponCallback.onSuccess(clazz);
                }
            });

        } catch (final IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mResponCallback.onFailure(e);
                }
            });

        }
    }

    @Override
    public void onFailure(Exception e) {
        mResponCallback.onFailure(new Exception("出错了... "));
    }


    public String getContent( InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str = sb.toString();
        return str;

    }

}
