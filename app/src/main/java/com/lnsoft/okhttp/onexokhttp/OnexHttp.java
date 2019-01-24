package com.lnsoft.okhttp.onexokhttp;

/**
 * des：
 * author：onexzgj
 * time：2019/1/23
 */
public class OnexHttp {

    public static<T,M> void sendHttp(T requestData,String url,Class<M> response,ResponCallback callback){

        HttpRequest jsonRequest=new JsonHttpRequest();

        CallBackListener listener=new JsonCallbackListener<>(response,callback);
        HttpTask task=new HttpTask(requestData,url,listener,jsonRequest);

        TheadPoolManager.getInstance().addRequestTask(task);


    }
}
