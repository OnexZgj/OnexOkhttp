package com.lnsoft.okhttp.onexokhttp;

import java.io.InputStream;

/**
 * des：
 * author：onexzgj
 * time：2019/1/23
 */
public interface CallBackListener {

    void onSuccess(InputStream in);
    void onFailure(Exception e);

}
