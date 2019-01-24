package com.lnsoft.okhttp.onexokhttp;

import java.io.IOException;

/**
 * des：响应CallBack
 * author：onexzgj
 * time：2019/1/23
 */
public interface ResponCallback {
    void onSuccess(Object obj);
    void onFailure(Exception e);

}
