package com.lnsoft.okhttp.onexokhttp;

/**
 * des：
 * author：onexzgj
 * time：2019/1/23
 */
public interface HttpRequest {

    void setUrl(String url);

    void setParams(byte[] params);

    void setListener(CallBackListener callBackListener);

    void execute();

}
