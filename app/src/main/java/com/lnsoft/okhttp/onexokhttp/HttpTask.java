package com.lnsoft.okhttp.onexokhttp;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * des：
 * author：onexzgj
 * time：2019/1/23
 */
public class HttpTask implements Runnable, Delayed {

    private  HttpRequest mRequest;

    /**
     * 重试次数
     */
    private int retryCount = 0;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public <T> HttpTask (T params, String url,
                         CallBackListener callBackListener, HttpRequest request) {
        this.mRequest=request;
        request.setUrl(url);
        request.setListener(callBackListener);
        if (params!=null) {
            String param = JSON.toJSONString(params);
            try {
                request.setParams(param.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        try {
            mRequest.execute();
        }catch (Exception e){
            TheadPoolManager.getInstance().addDelayTask(this);
        }

    }

    /**
     * 队列延时时间
     */
    private long delayTime;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis()+3000;
    }

    @Override
    public long getDelay(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.delayTime-System.currentTimeMillis(),TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed delayed) {
        return 0;
    }
}
