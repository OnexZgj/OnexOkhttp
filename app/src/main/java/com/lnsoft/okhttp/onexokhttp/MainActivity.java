package com.lnsoft.okhttp.onexokhttp;

import android.service.quicksettings.Tile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private HttpURLConnection mUrlConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.btn_get);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                //开启线程来发起网络请求
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HttpURLConnection connection = null;
//                        BufferedReader reader = null;
//                        try {
//                            URL url = new URL("http://www.wanandroid.com/project/tree/json");
//                            connection = (HttpURLConnection) url.openConnection();
//                            connection.setRequestMethod("GET");
//                            connection.setConnectTimeout(8000);
//                            connection.setReadTimeout(8000);
//                            InputStream in = connection.getInputStream();
//                            //对获取到的输入流进行读取
//                            reader = new BufferedReader(new InputStreamReader(in));
//                            StringBuilder response = new StringBuilder();
//                            String line;
//                            while ((line = reader.readLine()) != null){
//                                response.append(line);
//                            }
//
//                            Log.d("TAG", "run: " +response.toString());
//
////                            showResponse(response.toString());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }finally {
//                            if (reader != null){
//                                try {
//                                    reader.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            if (connection != null){
//                                connection.disconnect();
//                            }
//                        }
//                    }
//                }).start();







//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        URL url = null;
//                        try {
//                            url = new URL("http://www.wanandroid.com/project/tree/json");
//                            mUrlConnection = (HttpURLConnection) url.openConnection();
//                            mUrlConnection.setConnectTimeout(8000);
//                            mUrlConnection.setReadTimeout(8000);
//                            mUrlConnection.setRequestMethod("GET");// 提交模式
////                            mUrlConnection.setInstanceFollowRedirects(true);
////                            mUrlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
////                            mUrlConnection.connect();
////                            OutputStream out = mUrlConnection.getOutputStream();
////                            BufferedOutputStream bos = new BufferedOutputStream(out);
////                    if (mParams != null)
////                        bos.write(mParams);
//
////                            bos.flush();
////                            out.close();
////                            bos.close();
//
//                            if (mUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                                InputStream in = mUrlConnection.getInputStream();
//                                String content = getContent(in);
//                                Log.d("===>", "onClick: " +content);
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                            Log.d("===>", "onClick: " +e.getMessage().toString());
//                        }
//
//                    }
//                }).start();





                OnexHttp.sendHttp(null, "http://www.com/project/tree/json", Title.class, new ResponCallback() {
                    @Override
                    public void onSuccess(Object obj) {
                        Title title= (Title) obj;
                        System.out.println(title.toString());
                        Toast.makeText(MainActivity.this, title.getData().get(0).getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println(e.getMessage().toString());
                    }
                });
            }
        });



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
